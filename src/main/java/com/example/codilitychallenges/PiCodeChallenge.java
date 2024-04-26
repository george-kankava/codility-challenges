package com.example.codilitychallenges;

import java.util.HashSet;
import java.util.Set;

public class PiCodeChallenge {

    public  String [] sol(String p, String q) {
//        List<String> results = new ArrayList<>();
//
//        results.add(String.valueOf(p.charAt(0)));
//        for (int i = 1; i < q.length(); i++) {
//            List<String> res = new ArrayList<>();
//            for (String result : results) {
//                res.add(result + p.charAt(i));
//                res.add(result + q.charAt(i));
//            }
//            results.clear();
//            results.addAll(res);
//        }

        //  P = "abc", Q = "bcd"

        String [] resultsArr = new String[] { String.valueOf(p.charAt(0)) };

        for (int i = 1; i < p.length(); i++) {
            String [] resArr = new String[resultsArr.length * 2];
            int index = 0;
            for (String s : resultsArr) {
                resArr[index++] = s + p.charAt(i);
                resArr[index++] = s + q.charAt(i);
            }
            resultsArr = resArr;
        }

        return resultsArr;
    }

    public Integer countCharsInString(String [] words) {

        int [] count = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            count[i] = countDistinctChars(words[i]);
        }
//
//        List<Integer> charsCount = new ArrayList<>();
//        for (String word : words) {
//            List<Character> chars = new ArrayList<>();
//            int counter = 0;
//            for (int i = 0; i < word.length(); i++) {
//                if (!chars.contains(word.charAt(i))) {
//                    counter++;
//                }
//                chars.add(word.charAt(i));
//            }
//            charsCount.add(counter);
//        }

//        int [] count = new int[words.length];

//        for (int i = 0; i < words.length; i++) {
//            int counter = 0;
//            String word = words[i];
//            char [] characters = new char[word.length()];
//            for (int j = 0; j < word.length(); j++) {
//                boolean found = false;
//                for (char character : characters) {
//                    if (word.charAt(j) == character) {
//                        found = true;
//                        break;
//                    }
//                }
//                if (!found) {
//                    characters[j] = word.charAt(j);
//                    counter++;
//                }
//            }
//
//            count[i] = counter;
//        }

        int min = count[0];
        for (int j : count) {
            if (j < min) {
                min = j;
            }
        }


        return min;


    }

    public static int countDistinctChars(String str) {
        Set<Character> set = new HashSet<>();
        for (char c : str.toCharArray()) {
            set.add(c);
        }
        return set.size();
    }

    public int solution(String p, String q) {
        String[] pResults = sol(p, q);
        String [] qResults = sol(q, p);

        String [] resultsArr = new String[pResults.length + qResults.length];

        mergeArrays(pResults, resultsArr, qResults);

        return countCharsInString(resultsArr);

    }

    private static void mergeArrays(String[] pResults, String[] resultsArr, String[] qResults) {
        System.arraycopy(pResults, 0, resultsArr, 0, pResults.length);
        System.arraycopy(qResults, 0, resultsArr, pResults.length, qResults.length);
    }

    public static void main(String[] args) {
        String p = "jxrcnzofkqbwgtpmlhvnixyafda";
        String q = "mhjtvglqdczwfeoanxbipklsube";
        PiCodeChallenge solution = new PiCodeChallenge();
        int result = solution.solution(p, q);
        System.out.println(result);

    }
}