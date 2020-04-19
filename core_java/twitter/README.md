# TwitterCLI App 

## Introduction
TwitterCLI App aims to post, search, and delete Twitter posts by Twitter REST API. By completing this app, the 
This app is similar to Linux command `egrep -r {regex} {rootPath} > {outFile}`.Java I/O and Lambda will be used in this app.
The app also takes above three arguments: regex, rootPath, outFile.
+ regex: a special text string for describing a search pattern
+ rootPath: root directory path
+ outFile: output file name

## Instruction
Post Twitter: '"post" "tweet_text" "latitude:longitude"', e.g. '"post" "Hello Twitter!" "20:20"'
Show Twitter: "show" "tweet_id" "field1, field2", e.g. "show" "tweet_id" "text,coordinates"n 
`Program arguments` in Intellij following the format:
```.*regex.* ./rootPath /outFile``` in order to search `.*regex.*` pattern from `./rootPath` directory recursively and output the result to `/rootPath` file.
In order to achieve the goal, the workflow is defined as below:

```
matchedLines = []
    for file in listFiles(rootDir)
     for line in readLines(file)
       if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

listFiles(): This method iterate a given directory, and return all the files.\
readLines(): It reads a file and return all the lines.
containsPattern(): It checks if a line contains the regex pattern, which is passed by user in `Program arguments`.\
writeToFile(): It writes matched lines to a output file.\
JavaGrep interface(JavaGrep.java) declares these methods.\
JavaGrepImp class(JavaGrepImp.java) implements JavaGrep interface.

## Performance Issue and Solutions
ListFiles() and readLines() methods leaverage collections data structure, and data resides in memory, so when processing large files, it will consume lots of memory resource. In order to overcome the drawback of collections, streams are introduced to override listFiles() and readLines() methods because stream is not a data structure that stores data; instead, it conveys elements from a source such as data structure through a pipeline of computational operations.

## Improvements
Grep App can add new feature such as counting the frequency of the regex in given directory.\
Grep App can add line numbers to each line to make it more user-friendly.

