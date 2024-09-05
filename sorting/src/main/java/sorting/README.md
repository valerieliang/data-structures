# Discussion

## PART I: MEASURED IndexedList

**Discuss from a design perspective whether iterating over a `MeasuredIndexedList`should 
affect the accesses and mutation counts. Note that for the purposes of this assignment we are NOT 
asking you to rewrite the `ArrayIndexedListIterator` to do so. However, if you wanted to include 
the `next()` and/or `hasNext()` methods in the statistics measured, can you inherit 
`ArrayIndexedListIterator` from `ArrayIndexedList` and override the relevant methods, or not? 
Explain.**

Based on the current implementation of count, it would also make sense for the iterator to also 
increase the number of reads by one. Since going over the elements of the `MeasuredIndexList` in the 
`count()` method counts as accessing the elements, the iterator, which does something similar by 
iterating through the data structure, should also record the number of accesses. However, it would 
not make sense for the number of mutations to change, since while the value of the data being iterated 
over may change, it does not actually affect the value within the `MeasuredIndexedList`. Instead, for 
example, if you are iterating through the `MeasuredIndexedList` in order to change something within it, 
a separate class method would be the one to increase the number of mutations.

Based on the current implementation of the `ArrayIndexedListIterator` it would make sense to extend 
the iterator for the `MeasuredIndexedList` class directly from the `Iterable<T>` class since the 
`ArrayIndexedListIterator` is a private class, and private items from the super class are not 
inherited by the subclass. If you wanted to modify the number of reads you could implement the 
iterator as a nested class, and after checking for `hasNext()` in the `next()` method, if/when the 
next element is iterated over, increase the value for number of reads. Don't change the accessed value in 
`hasNext()` to avoid double-counting.


## PART II: EXPERIMENTS

**Explain the mistake in the setup/implementation of the experiment which resulted in a discrepancy 
between the results and what is expected from each sorting algorithm.**

One key error in the data is that the `descending` data set is not truly descending. For example, the 
data has a few "small" values (i.e: 999,998,99, etc.) scattered into the data every 10 or so numbers.
Typically, for a list already sorted in a correctly descending order, Insertion Sort would be the best 
algorithm. However, since it worked most efficiently on the other datasets (`ascending` and `random`), 
but not the `descending` data, it indicated that something was wrong with the data, rather than the code, 
since the performance was consistent between the Bubble, Selection, Gnome, Null, and Insertion Sorts in 
other scenarios.

Insertion Sort checks each element against the already sorted portion to determine where to 
"insert" it, and the smallest element would just go in the front. However, since there are non-descending 
numbers the sorting algorithm would have to check more parts of the list after the first "small" numbers 
are added. Thus, it makes sense why Insertion Sort works in a more efficient, expected speed for the 
ascending, and even random sorts, but not the descending one. Since it checks every number against the unsorted 
portion of the array, in the best case, this would be O(n), one iteration of the array. In worst case,
where the values have to be inserted every time, this would be O(n^2), since it would iterate n(n-1)/2 times.
For example, the insertion sort would only need to run the outer loop for the first 20 numbers (9999 to 
9990, 999, and 9989 to 9981.) and then check the first element of the sorted list. But for 9989, it would 
see that it's bigger than 999, and have to check 999 and the number after, slowing it down as it has to 
check an extra index and move more values. Then, for values 9989 to 9981, it would have to check 999 
and the number sorted after that. This extra step, while it seems insignificant at smaller datasets, would 
add more runtime as the size of the data increases, since there would be more random smaller numbers to check 
against before reaching the part of the list with the "expected" descending data.

On the other hand, Selection Sort searches for the smallest value in the list, and moving it to the front.
While this is generally a slower sort for ascending and random lists, since it requires iterating through 
several renditions of the same list, it works out in this case, since the smaller numbers are scattered in
"random" indices and the list isn't truly descending. In all cases, this would be O(n^2) time complexity, since the 
algorithm would need to continue to iterate through each remaining element of the array to find which is smallest, 
n(n-1)/2 times.
Using 20 numbers, selection sort iterates through numbers 9999 to 9990, 999, and 9989 to 9981. It would find the 
minimum value to be 999, and move that to the front, then it would work as a descending list for the other remaining 
numbers, swapping two numbers at a time. This ends up working out faster than the Insertion Sort when there are random 
smaller numbers scattered around the list, because Insertion Sort would also increase it's best case scenario time
from O(n) to O(n^2) if it has to run through iterations of its inner loop. This explains why the mutations are so low 
in this case, because it allows for few swaps.

Finally, Bubble Sort compares adjacent elements and swaps them if they are in the wrong order. A descending dataset is 
the worst scenario for Bubble Sort since it requires that almost every iteration requires a swap (which is why the 
mutations were so high). Having the non-descending values doesn't really affect Bubble Sort as much, since it would 
still need to do many of those swaps anyway. In best case, this would be O(n), since everything is already sorted and 
it would just have to iterate through the array. In worst case, descending, it would be about O(n^2) iterations, since 
essentially every pair of elements would be swapped.


## PART III: ANALYSIS OF SELECTION SORT

**Determine exactly how many comparisons C(n) and assignments A(n) are performed by this 
implementation of selection sort in the worst case. Both of those should be polynomials of degree 2 
since you know that the asymptotic complexity of selection sort is O(n^2).**

Given that the length of the array is n:
n^2 + 4n - 4 total assignments
n^2 total comparisons

Outer Loop:

LINE 3: 
- i = 0, +1 assignment
- incrementing 0 from until i = n-1, +(n-1) assignments
- compares i with (n-1) until i = (n-1) inclusive, +n comparisons
TOTAL: n assignments, n comparisons
The outer loop iterates n-1 times total. i.e: 0, 1, ... n-2 are all valid

LINE 4:
- assigns value of i to max, +1 assignment; because it occurs for every outer loop, it occurs 1*(n-2) times
TOTAL: n-2 assignments

LINE 10:
- assigns the value at the current index to temp, +1 assignments
LINE 11:
- assigns the highest value found to the current index, +1 assignments
LINE 12:
- assigns the stored temp value to the later index (originally containing the maximum value), +1 assignments
These three (1+1+1) assignments happen every time the outer loop is called.
The total number of assignments here would be (3)(n-1).
TOTAL: 3n-3 assignments

Inner Loop:
Note that in the worst case, the inner loop would always run until the end of the list.

LINE 5:
- j = i + 1, +1 assignment
- incrementing from i + 1 until n, +(n-i-1) assignments 
- compares (i+1) with n until (i+1) = n, inclusive, +(n-i) comparisons
These assignments each happen the n-1 times from the outer loop.
We can recognize the (n-i-1) pattern as n/2; this multiplied by n is Gauss's Sum: n(n-1)/2
TOTAL: n(n-1)/2 + 1 assignments, n(n-1)/2 comparisons

LINE 6:
- determines if a[j] is greater than a[max], +1 comparison
LINE 7: (assumes that the if statement is true for the worst case, so it always runs)
- assigns j to max, +1 assigment
Since these happen every time the inner loop is run, they occur n(n-1)/2 * 1 times each
TOTAL: n(n-1)/2 assignments, n(n-1)/2 comparisons

FIND TOTALS:
n assignments + n-2 assignments + 3n-3 assignments + n(n-1)/2 + 1 assignments + n(n-1)/2 assignments
n + n - 2 + 3n - 3 + n^2/2 - n/2 + 1 + n^2/2 - n/2
(1/2 + 1/2)n^2 + (1 + 1 + 3 - 1/2 - 1/2)(n) + (-2 - 3 + 1)(1)
n^2 + 4n - 4 total assignments

n comparisons + n(n-1)/2 comparisons + n(n-1)/2 comparisons
n + n^2/2 - n/2 + n^2/2 - n/2
(1/2 + 1/2)n^2 + (1 - 1/2 - 1/2)n
n^2 total comparisons