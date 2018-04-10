package com.microsoft.azure.sample.event;

import java.util.ArrayDeque;

public class TransactionsFIFO extends ArrayDeque<Transaction> {

    private int size = 100;

    public TransactionsFIFO(){
    }

    @Override
    public void push (Transaction transaction) {
        if (size() > size)
            pop();

        super.push(transaction);
    }

}