### DAA Assignment3 - Report and Defense (50 points)

##### Problem 1 and 2 - Number of Provinces (10 points)

The cities form an undirected graph. The input matrix shows direct connections.
A province is one connected group of cities.

**Problem 1: DFS**

The solution starts DFS from each city that has not been visited. DFS marks that
city and follows its connections. All cities reached by this search belong to
the same province. Each new search increases the answer by one.

The `visited` array prevents repeated visits. When the search ends, every city
in that province has been marked. A later search must start in another province.

Time is O(n^2), because the algorithm checks one matrix row for every city.
Extra space is O(n) for `visited` and the recursive calls.

**Problem 2: Disjoint Set Union**

At first, every city is a separate group, so the answer is n. For each direct
connection, the solution joins the two groups. The answer decreases only when
two different groups are joined. Connections inside the same group do not
change the answer.

The `find` method uses path compression. The `union` method attaches the smaller
group to the larger group. Only the upper half of the matrix is checked because
the graph is undirected.

Time is O(n^2 + e * alpha(n)), where e is the number of direct connections checked.
The function alpha(n) grows very slowly and comes from the DSU operations.
Extra space is O(n) for the parent and size arrays.

For `[[1,1,0], [1,1,0], [0,0,1]]`, both solutions return 2. Cities 0 and 1
belong to one group, and city 2 belongs to another group.

DFS explores connections directly. DSU joins groups without a graph traversal.

##### Problem 3 and 4 - Min Cost Spanning Tree (10 points)

Each point is a vertex. The cost of an edge is the Manhattan distance:
`abs(x1 - x2) + abs(y1 - y2)`. The answer is the cost of a minimum spanning tree.
It connects all n points with n - 1 edges and has no cycle.

**Problem 3: Kruskal**

The solution creates an edge for every pair of points and sorts the edges by
cost. It takes an edge when its endpoints are in different DSU groups. If the
endpoints are already connected, the edge is skipped because it would form a
cycle. The algorithm stops after taking n - 1 edges.

The cheapest edge joining two current groups is safe to add to a minimum
spanning tree. Repeating this choice connects all points with minimum total cost.

There are n * (n - 1) / 2 edges. Creating them takes O(n^2) time, and sorting
them takes O(n^2 log n) time. The total time is O(n^2 log n).
Extra space is O(n^2) for the edges and sorting, plus O(n) for DSU.

**Problem 4: Prim**

The solution starts from point 0. For each unused point, `minimumCost` stores
the cheapest edge connecting it to the current tree. The algorithm selects
the unused point with the smallest value, adds its cost, and updates the other
values using the new point.

At every step, it chooses the cheapest edge from the current tree to the
remaining points. This is a safe choice for a minimum spanning tree.

Time is O(n^2): each of the n steps scans the points to select and update them.
Extra space is O(n). Distances are calculated when needed, so there is no full
edge list.

For `[[0,0], [2,2], [3,10], [5,2], [7,0]]`, both solutions return 20.
For one point, the answer is 0 because no edge is needed.

Kruskal sorts all edges. Prim grows one tree and uses less memory in this version.

##### Problem 5 and 6 - Software Dependency Cycles (10 points)

Each dependency `[a,b]` is a directed edge from a to b. A group is a strongly
connected component: every module in the group can reach every other module.
An isolated module also counts as one group.

**Problem 5: Kosaraju**

The solution builds the graph and another graph with all edges reversed.
The first DFS records each module after all its outgoing edges have been
checked. This gives the finishing order.

The second pass follows the reversed graph, starting from the last finished
module. Each new search finds one group. This order chooses a group that cannot
reach another unvisited group in the reversed graph. Therefore, two different
groups are not counted as one.

Time is O(n + m), where m is the number of dependencies. Each pass visits every
module and edge at most once. Extra space is O(n + m) for the two graphs and
the traversal arrays.

**Problem 6: Tarjan**

The solution uses one DFS traversal. `discovery` stores when each module was
first visited. `low` records the earliest discovery number found through its
DFS subtree and links to modules still on the component stack.

An edge to an unseen module starts a new DFS step. After that child finishes,
its low value updates the parent. An edge to an active module can lower the
current value using that module's discovery number. Modules already removed
from the component stack do not update this value.

When `low[node] == discovery[node]`, the node is the root of a complete group.
The solution removes modules from the component stack up to this node and
increases the answer by one.

Time is O(n + m). Extra space is O(n + m), including the graph. The DFS stack
and the component stack each need O(n) space.

Both solutions use explicit stacks for DFS. A chain may contain 100,000 modules,
so recursive calls could overflow Java's call stack. An extra array remembers
which outgoing edge to check next. In Tarjan, the DFS stack stores the current
search path, while the component stack stores modules not yet assigned to a group.

For `n = 5` and `[[0,1], [1,2], [2,0], [2,3], [3,4]]`, the answer is 3:
`{0,1,2}`, `{3}`, and `{4}`. Kosaraju uses two passes and a reversed graph.
Tarjan uses one pass with discovery and low values.

##### Problem 7 and 8 - Course Schedule (10 points)

A prerequisite `[a,b]` means that b must be completed before a. The graph
therefore contains the edge `b -> a`. All courses can be completed exactly
when this directed graph has no cycle.

**Problem 7: DFS**

Each course has one of three states: 0 means unseen, 1 means active in the
current DFS path, and 2 means finished. A course becomes finished after all
its outgoing edges have been checked.

An edge to an active course closes a path back to a course that is still being
processed. This is a cycle, so the method returns false. If every search ends
without such an edge, it returns true. The outer loop also checks disconnected
parts of the graph. The DFS uses an explicit stack and a next-neighbor array.

**Problem 8: Kahn**

The indegree of a course is the number of prerequisites it still needs.
The solution first puts all courses with indegree zero into a queue.
After taking a course from the queue, it decreases the indegrees of the courses
that depend on it. A course enters the queue when its indegree becomes zero.

If all courses are processed, the method returns true. If some remain, they
are blocked by a cycle, and it returns false.

Both algorithms take O(n + m) time and O(n + m) extra space, including the graph.
The traversal arrays and the stack or queue use O(n) space.

For `n = 2` and `[[1,0]]`, the answer is true. Course 0 can be taken before
course 1. Adding `[0,1]` creates a cycle, so the answer becomes false.
DFS finds a cycle during the search. Kahn finds it when some courses cannot
be processed.

##### Problem 9 and 10 - Course Schedule II (10 points)

These problems use the same edge direction as Problems 7 and 8. The result must
contain every course once, with each prerequisite before the course that needs
it. More than one correct order may exist.

**Problem 9: DFS with reverse finishing order**

The solution uses the same three DFS states as Problem 7. When a course is
finished, it is placed into the result array from right to left. This creates
reverse finishing order without a separate reversal step.

For an edge `b -> a` in a graph without cycles, a finishes before b. Writing
the finishing order backward puts b before a. An edge to an active course
means there is a cycle, so the method returns an empty array.

**Problem 10: Kahn**

Courses with indegree zero are added to the result array. The same array is
used as a queue: `head` selects the next course, and `tail` is the next free
position. Processing a course can make other courses ready.

Each course is added only after all its prerequisites have been processed.
If the result contains fewer than n courses, there is a cycle, and the method
returns an empty array.

Both methods take O(n + m) time and O(n + m) extra space, including the graph.
The result and other traversal arrays each use O(n) space.

For `n = 4` and `[[1,0], [2,0], [3,1], [3,2]]`, DFS returns `[0,2,1,3]`
and Kahn returns `[0,1,2,3]` with the given input order. Both are correct:
0 comes before 1 and 2, and both 1 and 2 come before 3. For a cycle, both
methods return `[]`.
