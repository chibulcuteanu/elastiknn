package com.klibisz.elastiknn.lucene;

import org.apache.lucene.search.KthGreatest;

public class ConstantHitCounter implements HitCounter {

    private final int maxDocs;
    private final short constScore;
    private boolean isEmpty;

    public ConstantHitCounter(int maxDocs, short constScore) {
        this.maxDocs = maxDocs;
        this.constScore = constScore;
        this.isEmpty = true;
    }

    @Override
    public void increment(int key, short count) {
        isEmpty = false;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public short get(int key) {
        return constScore;
    }

    @Override
    public int numHits() {
        return maxDocs;
    }

    @Override
    public KthGreatest.Result kthGreatest(int k) {
        return new KthGreatest.Result(constScore, 0, maxDocs);
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {

            private int id = -1;

            @Override
            public void advance() {
                id++;
            }

            @Override
            public boolean hasNext() {
                return id + 1 < maxDocs;
            }

            @Override
            public int docID() {
                return id;
            }

            @Override
            public int count() {
                return constScore;
            }
        };
    }
}
