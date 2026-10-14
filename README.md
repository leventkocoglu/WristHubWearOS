# WorkFlow English — Engineer V10

10 complete modules: 40 engineering/logistics cases, 2 saved decision shifts, 8 numerical tools, 12 mini lessons, 40 English phrases, due-card review, daily case, local English writing support, progress/favorites/notes, themes and JSON backup. No network API or AI evaluation.

## Install / continuity
Uses application ID com.workflow.english.v10, versionCode 10. Installs beside V2 because the V2 build used a different ephemeral signing certificate. No automatic V2 data migration across the separate app identities. V10 JSON backups support future V10 restores. CI decodes a fixed public debug test key from signing/debug-test.keystore.b64; this is only a test key, not a production signing key. Android TextToSpeech depends on installed English voices.

## Models
Takt / capacity; OEE; EOQ; reorder point; Little law; steady-state M/M/1; exact bounded enumeration of two integer products under one capacity constraint; simplified Kanban count. All tools disclose assumptions/units, reject invalid input, and do not present M/M/1 finite waiting at lambda >= mu. Product mix enumeration capped at 10,000 A candidates. Shift backlog indicator is an educational score, separate from all queue models. Problem numeric tolerance is 0.5% or 0.005 for zero. All content is original; no book exercises are copied.

## Sources
Goldratt: What Is This Thing Called Theory of Constraints and How Should It Be Implemented? Concepts verified against https://www.toc-goldratt.com/en/about-toc and https://www.goldrattresearchlabs.com/introduction-to-toc . Lean takt: https://www.lean.org/lexicon-terms/takt-time/ . OEE: https://www.oee.com/calculating-oee/ . Queue topics also informed by user-supplied Birth–Death Processes notes (Smimulation Theory.pdf). Full book text not used.

## Build / tests
JDK 17, Gradle 8.9, Android SDK 35. `node tests/logic.cjs`; install Playwright 1.51.1 and Chromium, then `node tests/ui.cjs`; `gradle assembleDebug`. CI runs logic and responsive mobile UI tests, verifies the APK signature, and uploads APK and QA screenshots. Physical-device testing remains separate.
