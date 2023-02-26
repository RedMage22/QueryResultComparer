QueryResultComparer

To run app go to Gradle Tasks -> application -> run

It is assumed that:
- your csv files are comma delimited.
- your reference file has all the necessary data.
- your compared file has most or all the key data necessary for comparison.




Once processing is done the resulting file will contain the compared data and a summary at the end.
Data will be sorted alphanumerically by the key data.
If the compared file did not have a key found in the reference file, the compared column will contain 'no value'.
If a row's data does not match it will be assigned a FALSE value.
Records are counted and summarized at the bottom of the file.



