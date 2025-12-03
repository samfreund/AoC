#!/usr/bin/env fish

# Java file benchmarking script
# Usage: ./benchmark.fish <file.java|directory> [iterations]

if test (count $argv) -lt 1
    echo "Usage: $argv[0] <java-file|directory> [iterations]"
    exit 1
end

set target $argv[1]
set iterations 10

if test (count $argv) -ge 2
    set iterations $argv[2]
end

# Check if target exists
if not test -e $target
    echo "Error: '$target' not found"
    exit 1
end

function benchmark_file
    set java_file $argv[1]
    set iterations $argv[2]
    set results_var $argv[3]
    set file_num $argv[4]
    set total_files $argv[5]
    
    # Determine output destination (stderr if capturing, stdout otherwise)
    set out_fd 1
    if test -n "$results_var"
        set out_fd 2
    end
    
    # Extract class name from filename
    set class_name (basename $java_file .java)
    set file_dir (dirname $java_file)
    
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" >&$out_fd
    if test -n "$file_num"
        echo "[$file_num/$total_files] File: $java_file" >&$out_fd
    else
        echo "File: $java_file" >&$out_fd
    end
    echo "Class: $class_name" >&$out_fd
    echo >&$out_fd
    
    # Compile the Java file
    echo "Compiling..." >&$out_fd
    javac $java_file 2>&1
    if test $status -ne 0
        echo "✗ Compilation failed" >&$out_fd
        echo >&$out_fd
        return 1
    end
    
    echo "✓ Compilation successful" >&$out_fd
    echo >&$out_fd
    
    # Warm-up runs (JVM optimization)
    echo "Running warm-up iterations..." >&$out_fd
    for i in (seq 3)
        pushd $file_dir > /dev/null
        java $class_name > /dev/null 2>&1
        popd > /dev/null
    end
    
    echo "Starting benchmark ($iterations iterations)..." >&$out_fd
    echo >&$out_fd
    
    set -l times
    
    # Run benchmark iterations
    for i in (seq $iterations)
        set start (date +%s%N)
        pushd $file_dir > /dev/null
        java $class_name > /dev/null 2>&1
        popd > /dev/null
        set end (date +%s%N)
        
        set duration (math "($end - $start) / 1000000")
        set times $times $duration
        
        # Progress indicator
        set percent (math "floor($i * 100 / $iterations)")
        set bar_length (math "$percent / 5")
        set duration_int (math "floor($duration)")
        
        # Build progress bar
        set bar_length_int (math "floor($bar_length)")
        set filled (string repeat -n $bar_length_int "█")
        set empty (string repeat -n (math "20 - $bar_length_int") " ")
        
        # Print progress bar and stats on one line (to stderr to avoid capture)
        if test $i -eq $iterations
            printf "  Progress: [%s%s] %3d%% (Run %d/%d: %dms)\n" "$filled" "$empty" $percent $i $iterations $duration_int >&$out_fd
        else
            printf "  Progress: [%s%s] %3d%% (Run %d/%d: %dms)\r" "$filled" "$empty" $percent $i $iterations $duration_int >&$out_fd
        end
    end
    
    echo # New line after progress bar
    echo >&$out_fd
    echo "Results:" >&$out_fd
    
    # Calculate statistics
    set total 0
    for time in $times
        set total (math "$total + $time")
    end
    
    set avg (printf "%.2f" (math "$total / $iterations"))
    echo "  Average: {$avg}ms" >&$out_fd
    
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
    
    echo "  Min: {$min}ms" >&$out_fd
    echo "  Max: {$max}ms" >&$out_fd
    set range (printf "%.2f" (math "$max - $min"))
    echo "  Range: {$range}ms" >&$out_fd
    
    # Clean up class file
    rm -f $file_dir/$class_name.class
    
    # Return the average time and file name
    echo "$java_file|$avg"
    
    echo >&$out_fd
end

# Main logic
if test -f $target
    # Single file
    if string match -q "*.java" $target
        benchmark_file $target $iterations ""
    else
        echo "Error: '$target' is not a Java file"
        exit 1
    end
else if test -d $target
    # Directory - find all .java files recursively
    set java_files (find $target -type f -name "*.java" | sort)
    
    if test (count $java_files) -eq 0
        echo "No .java files found in '$target'"
        exit 1
    end
    
    echo "Found "(count $java_files)" Java file(s)"
    echo "Iterations per file: $iterations"
    echo
    
    set -l results
    set file_count 0
    
    for java_file in $java_files
        set file_count (math "$file_count + 1")
        set result (benchmark_file $java_file $iterations "capture" $file_count (count $java_files))
        set results $results $result
    end
    
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "Summary:"
    echo
    
    for result in $results
        set parts (string split "|" $result)
        if test (count $parts) -eq 2
            set file $parts[1]
            set avg_time $parts[2]
            echo "  $file: {$avg_time}ms"
        end
    end
    
    echo
    echo "Total files: "(count $java_files)
else
    echo "Error: '$target' is neither a file nor a directory"
    exit 1
end