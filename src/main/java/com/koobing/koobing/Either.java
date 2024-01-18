package com.koobing.koobing;

public sealed interface Either<L, R> {

    record Right<L, R>(R result) implements Either<L, R> {
    }

    record Left<L, R>(L error) implements Either<L, R> {
    }
}
