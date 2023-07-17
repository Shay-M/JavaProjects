# Game of Life Simulation

![Example](https://github.com/Shay-M/JavaProjects/blob/master/01-life/The%20Life%20Game/lifeOut.gif?raw=true)

simulate a game of life and save each generation as a pbm file.
simulation will be on a grid of W * H cells.
computation will be done using n threads ( by default n=2 )
the threads will be created once for all the life time of the simulation.
( do not create threads for each iteration ).
each iteration will result in a generation that we will save to a file named : life.001.pbm
with a running number for the frame sequence. The file will be formatted according to the format specified in https://en.wikipedia.org/wiki/Netpbm#PBM_example
the program shall take the following as arguments:

java Life filename iters threads   width height

filename: base file name for thee frames
iter: number of frames to create, default to 20
threads: number of threads to use, default to 2
width: width of the frame, default to 900
height: height of the frame, default to 800
