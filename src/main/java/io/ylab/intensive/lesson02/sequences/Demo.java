package io.ylab.intensive.lesson02.sequences;

public class Demo {
    public static void main(String[] args) {
        SequenceGeneratorImpl sequenceGeneratorImpl = new SequenceGeneratorImpl();

        System.out.println("---Sequences A---");
        sequenceGeneratorImpl.a(5);

        System.out.println("---Sequences B---");
        sequenceGeneratorImpl.b(5);

        System.out.println("---Sequences C---");
        sequenceGeneratorImpl.c(5);

        System.out.println("---Sequences D---");
        sequenceGeneratorImpl.d(5);

        System.out.println("---Sequences E---");
        sequenceGeneratorImpl.e(6);

        System.out.println("---Sequences F---");
        sequenceGeneratorImpl.f(6);

        System.out.println("---Sequences G---");
        sequenceGeneratorImpl.g(5);

        System.out.println("---Sequences H---");
        sequenceGeneratorImpl.h(7);

        System.out.println("---Sequences I---");
        sequenceGeneratorImpl.i(6);

        System.out.println("---Sequences J---");
        sequenceGeneratorImpl.j(8);
    }
}
