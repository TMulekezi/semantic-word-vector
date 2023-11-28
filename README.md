# Semantic-word-vector
This repository details a prototype Question Answering system by using
the semantic information embedded in a Distributional Semantic Model (DSM) file
called "glove.6B.50d_Reduced.csv" (included in the "resources" folder). For example, if "London" is the capital of the "UK", then we would like the system to identify that "Beijing" is the capital of "China"; or if the colour of "Apple" is "Red", then the colour of "Banana" should be "Yellow". Moreover, by providing a specific word, the system is able to find a list of words that are semantically close to it. E.g. By saying "Computer", the system should list "Software", "Technology", "Internet", "Computing" and "Devices" as the top 5 closest words. Or "Doctorate",
"Bachelor", "Thesis", "Dissertation" and "Graduate" should be the top 5 closest words
to "PhD".

The above was an assignment as part of my MSc in Computer Science course at the Univeristy of birmingham

## Theory
The theory behind finding the desired similarities is based on the idea of Distributional Semantic Models (DSM) also known as word space or vector space models. Each word has a vector associated with it (provided by the "glove.6B.50d_Reduced.csv" file) and by calucalting the consine similarity between these two vectors, a measure of semantic similarity can be ontained.

## Testing
Automatic JUnit tests were provided by the university and are found in the word_vector/src/test/java directory.

## Running the application
To run the application Java/JDK 17 must be installed in your personal device and can be run in any modern IDE such as IntelliJ and Eclipse.

