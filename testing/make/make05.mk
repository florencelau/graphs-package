foo.o: foo.c foo.a
        gcc -g -c foo.o foo.c

foo: foo.o
	gcc -o foo foo.o

foo.c: foo.k
	kacc -o foo.c foo.k
