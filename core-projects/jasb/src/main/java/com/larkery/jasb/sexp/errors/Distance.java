package com.larkery.jasb.sexp.errors;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

class Distance implements Comparator<String> {

    private final String to;
    private final Map<String, Integer> c = new HashMap<>();

    public Distance(final String to) {
        this.to = to;
    }

    @Override
    public int compare(final String arg0, final String arg1) {
        final int d1 = ld(arg0);
        final int d2 = ld(arg1);
        return Integer.compare(d1, d2);
    }

    /**
     * Dynamic programming levenshtien distance, with cache
     *
     * @param from
     * @return
     */
    private int ld(final String from) {
        if (c.containsKey(from)) {
            return c.get(from);
        } else {
            final int[][] distance = new int[from.length() + 1][to.length() + 1];

            for (int i = 0; i <= from.length(); i++) {
                distance[i][0] = i;
            }
            for (int j = 1; j <= to.length(); j++) {
                distance[0][j] = j;
            }

            for (int i = 1; i <= from.length(); i++) {
                for (int j = 1; j <= to.length(); j++) {
                    distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1,
                            distance[i][j - 1] + 1), distance[i - 1][j - 1]
                            + ((from.charAt(i - 1) == to.charAt(j - 1)) ? 0 : 1));
                }
            }

            final int val = distance[from.length()][to.length()];
            c.put(from, val);
            return val;
        }
    }

    public static final Comparator<String> to(final String to) {
        return new Distance(to);
    }
}
