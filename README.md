# Andrew's Shell (A-SH)

A collection of independent programs united inside a very simple user shell written in java. This is effectively an
exercise in I/O from and to file system by a custom user script.

## Actors & Features

- A User can:
    - Search for programs included in the shell and run them
    - Run commands not included in the shell like normal
    - Change working directory and print it out at will
    - Print files located in CWD

- The Application can:
    - Read and write to the file system
    - Accept User input from the console
    - Run programs from the command line if not in packaged with it by default

## TODO
- Clean up the excessive IF-ELSE statments in the ConsoleIO.java file.
- Use java.io.console instead of stdout.
