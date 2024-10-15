### Overview
This command-line tool takes user inputs and creates a matrix from them. Then, it solves the system of linear equations using row reduction and returns if the system has zero, one, or infinite solutions, and outputs the solution if it is unique.
To row reduce, the first pivot is identified, and all vairables below it are eliminated using elementary row operations. Then, the next pivot is found and this process is repeated until there are no more pivots.
The tool then assesses how many solutions the system has, and solves for the solution by backsolving if the solution is unique.
