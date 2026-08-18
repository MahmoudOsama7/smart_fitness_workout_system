# smart_fitness_workout_system
Smart fitness workout system 

Database Schema:

### **Table: `workout_history`**

| Column Name | SQLite Data Type | Primary Key | Constraints / Default | Description |
| :--- | :--- | :---: | :--- | :--- |
| `id` | `INTEGER` | **Yes** | `AUTOINCREMENT` | Unique identifier for each session |
| `exerciseName` | `TEXT` | No | `NOT NULL` | Name of the exercise (e.g., "Barbell Squat") |
| `currentSet` | `INTEGER` | No | `DEFAULT 1` | Active set index |
| `totalSets` | `INTEGER` | No | `NOT NULL` | Total target sets for the workout |
| `completedSets` | `INTEGER` | No | `DEFAULT 0` | Total successfully completed sets |
| `weightKg` | `REAL` | No | `NOT NULL` | Resistance weight in kilograms |
| `elapsedTimeSeconds` | `INTEGER` | No | `DEFAULT 0` | Total active session duration in seconds |
| `remainingRestSeconds` | `INTEGER` | No | `DEFAULT 60` | Configured rest interval between sets |
| `timestamp` | `INTEGER` | No | `NOT NULL` | Unix timestamp (ms) when the session completed |
| `syncStatus` | `TEXT` | No | `DEFAULT 'PENDING_SYNC'` | Status flag (`SYNCED` vs `PENDING_SYNC`) |


### **DAO Operations (`WorkoutDAO`)**

* **`addWorkoutSession(entity): Long`** — Inserts a new workout session or replaces on conflict ID (`OnConflictStrategy.REPLACE`).
* **`getWorkoutSessionById(sessionId): WorkoutSessionEntity?`** — Performs a one-shot query for a single session by primary key.
* **`getAllWorkoutSessions(): Flow<List<WorkoutSessionEntity>>`** — Emits a reactive, live stream of workout history sorted newest first (`ORDER BY timestamp DESC`).
* **`getUnsyncedWorkouts(): List<WorkoutSessionEntity>`** — Retrieves all local records where `syncStatus = 'PENDING_SYNC'` for background sync recovery.
* **`markAsSynced(id)`** — Updates `syncStatus` to `SYNCED` upon receiving a successful network HTTP


Build commands 
1- Build Debug APKs Demo-> ./gradlew assembleDemoDebug
2- Build Debug APK Production -> ./gradlew assembleProductionDebug
3- Build Release APK Demo -> ./gradlew assembleDemoRelease
4- Build Release APK Production -> ./gradlew assembleProductionRelease

generated apks are found inside app/build/outputs/apk


State Pattern illustration 

## State Pattern Architecture

The workout session lifecycle is managed using the State Pattern. Behavior is encapsulated inside specific state objects, allowing the system to change its rules dynamically as the workout progresses without using complex conditional logic.

### State Transitions Flow

1. **ReadyState**: Initial idle state. Triggering `startWorkout()` resets set counts and transitions to `ActiveSetState`.
2. **ActiveSetState**: Represents an active exercise set.
    - `completeSet()` increments the completed sets counter. If sets remain, it launches the rest timer and transitions to `RestTimerState`. If all sets are complete, it transitions to `WorkoutCompletedState`.
    - `pauseWorkout()` transitions to `PausedState`.
3. **RestTimerState**: Manages the interval countdown between sets.
    - Ticks update the remaining rest duration.
    - When the countdown reaches zero or when `skipRest()` is called, it increments the current set and transitions back to `ActiveSetState`.
4. **PausedState**: Holds a reference to the `previousState` (whether `ActiveSetState` or `RestTimerState`) so `resumeWorkout()` restores the exact state and remaining timer duration.
5. **WorkoutCompletedState**: Terminal state indicating the workout is finished and ready for saving/syncing.

---

### Thread Safety & Concurrency Guarding

- **Mutex Protection**: A Kotlin `Mutex` wraps all state mutations to ensure atomic execution across coroutines.
- **Immediate Job Cancellation**: Any transition out of `RestTimerState` (`skipRest`, `pauseWorkout`, or `endWorkout`) immediately cancels the background timer coroutine under the mutex lock. This guarantees that 0ms race conditions cannot double-advance sets or leak background jobs.
- this is done using WorkEngine class and interface state and then creating needed classes to express these states and being
- controlled by WorkEngine 
