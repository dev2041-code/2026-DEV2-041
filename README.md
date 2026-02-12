# Berlin Clock Kata

The Berlin Clock (Mengenlehreuhr) is a unique time display that uses colored lamps instead of digits. This implementation showcases:

- **Test-Driven Development (TDD)** with 33 unit tests and 100% domain coverage
- **Clean Architecture** with clear separation of concerns
- **Modern Android** using Jetpack Compose, Hilt, StateFlow
- **UIState Pattern** - single source of truth following Google's recommended architecture
- **Type Safety** - no magic strings or raw values
- **Production-grade quality** tooling (ktlint, detekt, JaCoCo)

### How It Works

The Berlin Clock displays time using colored lamps:
- **Seconds**: Yellow lamp blinks (on for even seconds, off for odd)
- **Hours**: Two rows of red lamps (top = 5-hour blocks, bottom = 1-hour blocks)
- **Minutes**: Two rows of lamps (top = 5-minute blocks with quarter markers, bottom = 1-minute blocks)

## Getting Started

### Prerequisites

- Android Studio Ladybug | 2024.2.1 or later
- JDK 17
- Android SDK with API 35
- Git

### Installation
```bash
# Clone the repository
git clone https://github.com/dev2041-code/2026-DEV2-041.git

# Build the project
./gradlew build

# Run tests
./gradlew testDebugUnitTest

# Install on device/emulator
./gradlew installDebug
```
### Running the App

1. Open project in Android Studio
2. Click **Run** or `Shift + F10`
3. Select device/emulator
4. App launches with Berlin Clock display

## Testing

### Test Coverage
```bash
# Run all tests
./gradlew testDebugUnitTest

# Generate coverage report
./gradlew jacocoTestReport

# View report
open app/build/reports/jacoco/jacocoTestReport/html/index.html

### Running Specific Tests
```bash
# Run converter tests only
./gradlew testDebugUnitTest --tests BerlinClockConverterTest

# Run with coverage
./gradlew testDebugUnitTest jacocoTestReport
```

## Code Quality

### Quality Gates

All code passes strict quality checks:
```bash
# Kotlin style enforcement
./gradlew ktlintCheck

# Static code analysis
./gradlew detekt

# Run all quality checks
./gradlew ktlintCheck detekt testDebugUnitTest
```
### Code Quality Tools Configuration

**ktlint:**
- Kotlin 1.1.1 ruleset
- Android-specific rules enabled
- Automatic formatting on commit

**detekt:**
- Custom rule configuration
- Complexity thresholds enforced
- Naming conventions checked

**JaCoCo:**
- Domain layer: 100% coverage required
- HTML and XML reports generated
- Excludes UI, DI, and generated code

---

## Version History

### v2.0.0 - Architectural Improvements (February 12, 2026)

Complete architectural refactor addressing technical feedback from BNP Paribas evaluation:

**Improvements:**
- **Result Type Error Handling**: Replaced exception-based error handling with `Result<T>` pattern (Success/Failure)
- **UseCase with Business Logic**: Moved validation from Converter to UseCase - no longer a thin wrapper
- **Separated State from Presentation**: `LampState` (ON/OFF) is now distinct from `LampColor` (RED/YELLOW)
- **MaterialTheme Integration**: Components use `MaterialTheme.colorScheme` instead of hardcoded colors
- **Proper Separation of Concerns**: Clear boundaries between domain, presentation, and UI layers

**Technical Details:**
- Domain layer remains pure with no Android dependencies
- UseCase handles validation and returns `Result<BerlinTime>`
- Converter performs pure transformation only
- ViewModel gracefully handles errors with when expressions
- UIState includes error field for user feedback
- 100% domain coverage maintained with updated tests

**Commit:** [Link to v2.0.0 tag]

### v1.0.0 - Initial Submission (February 10, 2026)

Production-grade implementation with excellent fundamentals:

**Strengths:**
- Test-Driven Development with 33 unit tests
- Clean Architecture with proper layer separation
- 100% domain layer coverage
- Hilt dependency injection
- Single source of truth (UIState pattern)
- StateFlow reactive streams
- Data-driven actions (low complexity)
- Comprehensive quality tooling (ktlint, detekt, JaCoCo)

**Learnings:**
- Exception-based error handling needed improvement
- UseCase required more business logic beyond delegation
- Domain model mixed state with presentation concerns
- Colors should use MaterialTheme instead of being hardcoded

