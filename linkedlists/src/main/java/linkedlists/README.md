# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------

In my opinion, the best implementation would be the `ArrayIndexedList`:

Specifically, a `SparseIndexedList` is not a reasonable implementation. 
This list would contain all the values that differ from a default value, 
which would be all the values of the `Roster` since each student has their
own email.

Thus, in a debate between the `LinkedIndexList` and `ArrayIndexedList,` 
the competition is largely defined by flexibility of size and ease of 
navigation. 

The `Roster` class doesn't need the size flexibility provided 
by linked lists. The roster has a set maximum number of students, and 
realistically, shouldn't go past that. This implementation is most supported 
by the `ArrayIndexedList,` since its structure is based on an array with an 
upper limit that cannot be changed.

Moreover, the dynamic sizing of the `LinkedIndexList` is unnecessary. It is 
unlikely that the number of students will be constantly changing. Most students
start join the school at the beginning of fall semesters. It is better to just take 
this initial number, put it in an array, and if the school still has capacity (i.e:
roster isn't full), then we can add a few more students if they join in the Spring.

Finally, the array format is best for binary search. It could be awkward to do binary 
search on a linked list, since you would then need to sort the linked list and find the 
address of its middle node, which could be awkward and time-consuming. Thus, the 
`ArrayIndexedList`, where the indexes are an inherent part of the data structure which 
allows easy access to the middle of the array, is the best option.