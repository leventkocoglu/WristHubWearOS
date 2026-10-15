# WorkFlow Engineer V20

Installs as an update to V10: same application ID `com.workflow.english.v10`, versionCode 20, same explicit fixed debug test key. Persistent store/schema remains backward-compatible with V10; existing problems, XP, notes and cards remain. V2 is a separate app identity and does not migrate automatically.

## New application layer

- Deterministic five-day factory model with 480 one-minute time buckets per day, three serial stations and one product.
- Three scenarios: assembly constraint, demand surge and market constraint.
- User controls 0–120 units of paced daily raw release plus one daily action. Fixed upgrades cannot be purchased twice; daily overtime is temporary.
- Inputs drive integer processing completions, WIP, finished goods, deterministic yield/scrap, delivery, backlog, on-time demand, cash and throughput accounting proxy.
- Read-only preview, reversible last day, saved daily English drafts, five-day chart and comparison to a fixed reference plan (not claimed optimal).
- Eight reproducible numeric exercise generators: takt, OEE, EOQ, ROP, Little, M/M/1, Kanban and FPY. 40 core cases remain; variants are not counted as extra subjects.
- Optional Turkish restatement for all 40 cases; approach-only hints; three-step onboarding and five-part learning route.
- Backups include the new factory and exercise records, validate structure and reject malformed data. Old V10 backups receive defaults for the new modules.

## Factory model and accounting

Start: cash 10,000 TL, 12 jobs before assembly, four before packing, no raw or finished goods. Historical cost of opening WIP is part of initial conditions. Nominal capacities 70/40/60 units/day, infinite buffers, no blocking, work-in-progress processing credit carries across days. Downstream is processed before upstream in each minute. Day three assembly capacity is multiplied by 0.75 unless maintenance was purchased. Quality is deterministic yield credit, initially 90%, upgrade to 98%; no random defect claim. All station processing is integer; quality credit can carry over.

Daily revenue is delivered units ×100 TL. Purchases are released units ×20 TL. Operating cash cost is 1000/day plus 400 if assembly overtime is chosen. Other action costs are investments and apply once. Cash is tracked from those actual model flows. Sales throughput proxy is delivered units ×80 TL, explicitly distinct from cash. Delivery at day end satisfies old backlog first; current-day on-time demand is measured after that. Total shipments never exceed cumulative demand. No financing, inventory carrying cost, shared labor or stochastic failure process is represented. No real-factory forecasting claim.

Core V10 content retained: 40 fixed cases, 12 lessons, 40 English phrases, two qualitative decision shifts, eight calculators, reviews, favorites, notes and themes. Qualitative shifts remain explicitly separate from the numerical factory model. English checks remain local checklists, not AI grammar evaluation. TTS depends on installed device voices.

## Sources and validation

Original educational content inspired by E. M. Goldratt's *What Is This Thing Called Theory of Constraints and How Should It Be Implemented?*; full book/exercises not copied. TOC: https://www.toc-goldratt.com/en/about-toc . Lean: https://www.lean.org/lexicon-terms/takt-time/ . OEE: https://www.oee.com/calculating-oee/ . User-supplied Birth–Death Processes notes informed queue topics.

Build with JDK17, Gradle8.9, SDK35. Decode signing/debug-test.keystore.b64 to signing/debug-test.keystore before local builds; test key is public and not for production publishing. Run `node tests/logic.cjs`, install Playwright1.51.1/Chromium, run `node tests/ui.cjs`, then `gradle assembleDebug`. CI verifies all checks and the APK signature. Physical-device testing is separate.
