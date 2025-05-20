package ed.lab;

import java.util.*;

public class E02AutocompleteSystem {
    private final Map<String, Integer> sentenceFreq = new HashMap<>();
    private final StringBuilder currentInput = new StringBuilder();

    public E02AutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            sentenceFreq.put(sentences[i], sentenceFreq.getOrDefault(sentences[i], 0) + times[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            String sentence = currentInput.toString();
            sentenceFreq.put(sentence, sentenceFreq.getOrDefault(sentence, 0) + 1);
            currentInput.setLength(0);
            return new ArrayList<>();
        }

        currentInput.append(c);
        String prefix = currentInput.toString();
        PriorityQueue<String> pq = new PriorityQueue<>(
                (a, b) -> {
                    int freqCompare = sentenceFreq.get(b) - sentenceFreq.get(a);
                    if (freqCompare == 0) {
                        return a.compareTo(b);
                    }
                    return freqCompare;
                });

        for (String sentence : sentenceFreq.keySet()) {
            if (sentence.startsWith(prefix)) {
                pq.offer(sentence);
            }
        }

        List<String> top3 = new ArrayList<>();
        int count = 0;
        while (!pq.isEmpty() && count < 3) {
            top3.add(pq.poll());
            count++;
        }
        return top3;
    }
}