from=0
max=100

echo "primes from $from to $max are"

for n in $(seq $from $max); do
	./compile tests/isPrime
	echo $n | ./a.out | grep "is prime" > /dev/null
	if [ $? -eq 0 ]; then
		echo $n
	fi;
done
