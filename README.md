Circuit States Flow:
CLOSED (normal) 
    ↓ (failure rate > 50% in last 10 calls)
OPEN (block all calls for 10 seconds) 
    ↓ (after 10 seconds wait-duration)
HALF_OPEN (allow 1 test call)
    ↓ (if test call succeeds → CLOSED, if fails → OPEN again)

1. wait-duration-in-open-state: 10s
Meaning: How long the circuit stays OPEN (no requests allowed) before trying again

Behavior: After 10 seconds in OPEN state, circuit moves to HALF_OPEN state to test if service recovered

Purpose: Gives the failing service time to recover

2. failure-rate-threshold: 50
Meaning: The percentage of failed calls that will trigger the circuit to open

Behavior: When 50% of calls in the sliding window fail, circuit trips to OPEN state

Example: If 5 out of 10 calls fail (50%), circuit opens

3. sliding-window-size: 10
Meaning: The number of recent calls used to calculate the failure rate

Behavior: Only considers the last 10 calls when calculating the failure percentage

Purpose: Uses recent history, not all-time history
