# WorkFlow English V2

Offline Android learning app with 15 logistics cases, a six-decision shift with saved outcomes and drafts, 24 English phrase cards, TOC learning notes and an M/M/1 queue laboratory.

## Models
The shift uses explicit educational consequence scores and is not a numerical queue simulation. The queue laboratory uses analytical steady-state M/M/1 formulas with Poisson arrivals, independent exponential service times, one server, FCFS and unlimited capacity/population. Finite steady-state averages require arrival rate below service rate. A separate four-hour deterministic fluid backlog example is clearly labeled.

## English support
Local phrase/action/time checks and a few explicit error patterns; no AI grammar analysis. Android TextToSpeech uses the installed phone engine and available English voice. Phrase reviews are self-rated, ordered by due time, and scheduled at 10 minutes or 1/3/7 days.

## TOC source framework
E. M. Goldratt, What Is This Thing Called Theory of Constraints and How Should It Be Implemented?; https://www.toc-goldratt.com/en/about-toc and https://www.tocinstitute.org/five-focusing-steps.html. Original scenarios, no full book text or reproduced exercises. Queue topic also informed by the user-supplied Birth-Death Processes lecture notes (Smimulation Theory.pdf).

## Build and validation
JDK 17, Gradle 8.9, Android SDK 35. Run `node tests/logic.cjs`, `npm install --no-save playwright@1.51.1`, `npx playwright install chromium`, `node tests/ui.cjs`, then `gradle assembleDebug`. GitHub Actions runs checks and uploads the APK.

V2 uses application ID com.workflow.english.v2 and installs alongside V1, retaining V1 data separately. No automatic migration of V1 progress. A cached debug key supports subsequent V2 updates while the cache remains available. Debug builds are for testing.
