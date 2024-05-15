# Ice-Sliding-Puzzle
Used path-finding algorithms (BFS) to solve a type of sliding puzzle problem

The basic version that we will be dealing with is this:

.....0...S
....0.....
0.....0..0
...0....0.
.F......0.
.0........
.......0..
.0.0..0..0
0.........
.00.....0.

The player starts at the location labeled “S” and wants to reach the finish, labeled “F”. Each
turns they choose one of the four cardinal directions to move. However, except for S and F, the
floor is covered in frictionless ice, so they will keep sliding in the chosen direction until they
hit the wall surrounding the area, or one of the rocks (labeled “0”). For example, starting in
the map given above:

.....0...@
....0.....
0.....0..0
...0....0.
.F......0.
.0........
.......0..
.0.0..0..0
0.........
.00.....0.
the player (“@”) moving left would end up here:
.....0@..S
....0.....
0.....0..0
...0....0.
.F......0.
.0........
.......0..
.0.0..0..0
0.........
.00.....0.
So we are dealing with the problem of finding a path from S to F, but the reachability relation
between points is not the usual one.

The BFS algorithm with Priority Queue finds the shortest path from the start to the finish in any given map, if one exists. It should output all the steps of the solution it found, e.g., for the example above:

1. Start at (10,1)
2. Move left to (7,1)
3. Move down to (7,2)
4. Move left to (6,2)
5. Move down to (6,10)
6. Move right to (8,10)
7. Move up to (8,8)
8. Move right to (9,8)
9. Move up to (9,6)
10. Move left to (3,6)
11. Move up to (3,1)
12. Move left to (1,1)
13. Move down to (1,2)
14. Move right to (4,2)
15. Move down to (4,3)
16. Move left to (2,3)
17. Move down to (2,5)
18. Done!

Where the squares are numbered left to right, top to bottom.
