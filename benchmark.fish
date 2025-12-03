#!/usr/bin/env fish

# Java file benchmarking script
# Usage: ./benchmark.fish MyProgram.java [iterations]

if test (count $argv) -lt 1
    echo "Usage: $argv[0] <java-file> [iterations]"
    exit 1
end

set java_file $argv[1]
set iterations 10

if test (count $argv) -ge 2
    set iterations $argv[2]
end

# Check if file exists
if not test -f $java_file
    echo "Error: File '$java_file' not found"
    exit 1
end

# Extract class name from filename
set class_name (basename $java_file .java)

echo "=== Java Benchmark ==="
echo "File: $java_file"
echo "Class: $class_name"
echo "Iterations: $iterations"
echo

# Compile the Java file
echo "Compiling..."
javac $java_file
if test $status -ne 0
    echo "Compilation failed"
    exit 1
end

echo "Compilation successful"
echo

# Warm-up runs (JVM optimization)
echo "Running warm-up iterations..."
for i in (seq 3)
    java $class_name > /dev/null 2>&1
end

echo "Starting benchmark..."
echo

set -l times

# Run benchmark iterations
for i in (seq $iterations)
    set start (date +%s%N)
    java $class_name > /dev/null 2>&1
    set end (date +%s%N)
    
    set duration (math "($end - $start) / 1000000")
    set times $times $duration
    
    echo "Run $i: {$duration}ms"
end

echo
echo "=== Results ==="

# Calculate statistics
set total 0
for time in $times
    set total (math "$total + $time")
end

set avg (math "$total / $iterations")
echo "Average: {$avg}ms"

# Find min and max
set min $times[1]
set max $times[1]
for time in $times
    if test $time -lt $min
        set min $time
    end
    if test $time -gt $max
        set max $time
    end
end

echo "Min: {$min}ms"
echo "Max: {$max}ms"
echo "Range: "(math "$max - $min")"ms"

# Clean up class file
rm -f $class_name.class
