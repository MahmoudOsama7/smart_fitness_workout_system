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
