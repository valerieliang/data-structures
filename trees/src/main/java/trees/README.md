# Discussion

## Unit testing TreapMap
Even when using a fixed seed, there would still be issues in priority. 
- Consider my removeLeafRightLeftRotation.
- Using seed 1234 would cause the treap to have an order of 3 -> 2 -> 1 (n-height) because the first three priorities happen to be in decreasing order. 
- I changed the seed, so I could actually determine if my treap rotated.
- Changing the seed to 333 worked and caused the rotated tree to change from 3 -> 2 -> 1 to 1 <- 2 -> 3
- I would run the map.toString() method to determine what the priority would be for each key value pair, then write an assert statement based on the output (similar to AVL tree test).

However, this meant I had to strategically select values such that the order and priority pairs corresponded to create a treap.
- For example, keeping the seed as 333, the resulting binary tree after all insertions/rotations would be  (1 -> 2) <- 3 for the insertLeftRightRotation() test.
- This is unbalanced to the left (i.e: 2 is a child of 1 rather than being pulled to the top).
- My first instinct was to modify the values so that it would rotate properly.
- However, I quickly realized that this was not the solution as the priorities do not change with a set seed.
- Instead, by using the map.toString options and playing around with various different seeds, I could confirm that the rotations were being changed with respect to priority, not just an error in the rotation algorithm.
- While this required more work of manually checking every function one by one, it also gave me the liberty of being able to determine how priorities affected my treap.
- For example, the toString() of removeTwoChildrenNoRotation() revealed that the fourth priority was 903726277. While this worked out in the resulting treap, as the values were:
- 2:b:-1397264289
  1:a:903726277 4:d:-463472126
- it was interesting to note that the 4th value was an exceedingly large number, since the first three values were all negative, thus the difference in priority would be quite great.
- These results are reflected in removeTwoChildrenSingleRotation() as the resulting treap has an odd structure as the result of many smaller values having worse priorities. 
- j:j:-2051662409
  e:e:-1914786665 k:k:-496774307
  d:d:-1397264289 f:f:-1086614523 null null
  c:c:532376657 null null i:i:-463472126 null null null null
  b:b:903726277 null null null null null h:h:1949227088 null null null null null null null null null
  a:a:1416667966 null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null

## Benchmarking
test data in units of ms/op

hotel_california:
- arrayMap: 0.198 
- avlTreeMap: 0.159
- bstMap: 0.177
- treapMap: 0.212

federalist01:
- arrayMap: 2.173
- avlTreeMap: 0.894
- bstMap: 0.865
- treapMap: 1.033

pride_and_prejudice:
- arrayMap: 1303.677
- avlTreeMap: 82.806
- bstMap: 104.041
- treapMap: 97.871

moby_dick:
- arrayMap: 6966.801
- avlTreeMap: 169.299
- bstMap: 190.447
- treapMap: 192.169

observations:

In small data sets, using a tree/treap doesn't have much of an advantage over the normal arrays. 
This is demonstrated most clearly as the arrayMap operations were ~0.01 ms faster than the treap operations in hotel_california.
This is likely because arrays don't require any complex operations when finding/modifying an option.
For example, time is lost when rotating a subtree or swimming up/down in a treap. 
In small data sets, where linear time operations and lg time operations don't have that much of a difference (i.e: lg 2 is much closer to 2 than lg 2000 is to 2000),
trying to keep things sorted more clearly in a tree data structure can create an overall loss of time.
This is why in the larger data sets, the tree/treap structures are much faster (i.e: binary data structures are all under 200 ms/op, but arrayMap is 6966.801 ms/op for moby_dick dataset).
They allow for lg searches in O(lg n) time, which iterates over the data sets much more quickly than a linear O(n) search.

Likewise, AVL trees tend to outperform unbalanced BSTs in larger data structures, but they don't hold much of an advantage in smaller data sets.
In particular, avlTreeMap was approximately 20 ms/op faster than bstMap in both pride_and_prejudice and moby_dick, but the two only differed by about less than half a ms/op for smaller datasets.
This could be because the data sets are strings, and it is unlikely that strings will be inserted in ascending/descending order that results in an n height BST.
Thus, the difference between a BST and AVL tree in searching time is negligible, and the time saved in an AVL's search is spent on rotating the tree.
In larger datasets, AVL trees typically outperform unbalanced BSTs due to their self-balancing property. 
This balanced nature leads to better worst-case time complexities making AVL trees more suitable for scenarios where the dataset is expected to grow significantly over time.

Finally, the AVL tree outperformed the Treap in every experiment, although it wasn't by particularly large amounts (i.e: compared to a BST).
AVL trees are more efficient for datasets where the data distribution is relatively uniform. 
This is likely why the AVL ran ~20 ms/op faster than the treap for the book datasets, where there is a variety of language (opposed to repetitive lines in hotel_california).
The AVL tree ensures that the tree remains balanced, leading to better overall performance. 
Treaps, on the other hand, rely on randomization to maintain balance, which might not perform as well.
It could be possible (though implausible) that the priorities generated were consistently unlucky, leading to runtimes closer to O(n) than O(lg n) for the treap.