# Regular Expression Compiler

*IMPORTANT! if you are here to plagiarize my Regex Assigment,<br>
id encourage you to use this as a resource to learn and not just to steal. <br> The fact of the matter is, if you are cheating,you are only cheating yourself* <br>- Message supported by myself and contribtor Luke Finlayson<br>
The tutor is also making sure you won't be cheating XD<br> <br>
An implementation of a regular expression parser, compiler and matcher. Consists of two main programs, `REcompile` and `REsearch` that
can be used together to search through files for instances of the given regular expression. Under the hood a finite state machine is built
and shared between the two programs.

Blank lines will result in no matches as there is no content, even if the pattern matches with nothing.

## Usage

To compile a regex expression:

- `java REcompile [expression]`

To search through a file for pattern instances (FSM is read from standard input)

- `java REsearch [filename]`

See [here](https://github.com/luke-finlayson/regex/wiki/Operations-&-Expressions) for valid regex syntax and full documentation.
