# WorkFlow Engineer V40

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

## V40 additions

V40 keeps V20's package identity, fixed debug test signing key and schema10 store for an in-place update. It adds a two-product planning studio, three resources, an upfront materials+overtime budget, nominal demand caps and period fixed expenses. Each resource can receive 0/60/120 purchased overtime minutes. All nominal production is sold within nominal demand caps. Objective: period sales less material, purchased overtime and fixed period expense. The budget restricts only materials and overtime; the fixed expense is not in that upfront budget. Positive per-unit sales contributions allow exact enumeration over integer A quantities with maximum feasible B for each A; joint overtime optimization checks 27 combinations. It is not a general job-shop scheduler or arbitrary MILP solver. No setups, ordering, precedence, WIP or within-period due dates are represented in this module. The original V20 factory model remains separate.

Four fixed scenarios cover changing resource pressure, a high-price product, a tight material budget and low market demand. Plans can be saved (last12), restored and compared. A marginal experiment adds60 free base resource minutes while holding the chosen overtime, budget and demand unchanged; this is a discrete counterfactual, not a formal shadow price.

Risk analysis uses250 reproducible LCG-seeded samples: independent uniform demand multipliers0.8–1.2 rounded to units; independent base capacity multipliers0.75–1.10 floored to minutes. Purchased overtime is then added. Chosen A-first/B-first execution priority consumes realized resource capacities and caps production at planned quantities. Sales are capped at realized demand. Material cost is charged for all produced units, including unsold goods; no inventory valuation is credited to cash contribution. Quantiles are sorted-sample floor(p×249) order statistics. Result frequencies describe these assumed draws, not real-world probabilities, confidence intervals or guarantees. Same seed, plan and case reproduce the results.

Three scripted management-English dialogues (nine turns) add first-completion rewards with duplicate protections. Twenty added terms append to the existing forty without moving indices. Old backups default the new studio record; new backups validate all plans, dialogue records and risk references. Tests cross-check the optimizer against independent exhaustive A/B/overtime enumeration, verify resource/budget/sales/cash bounds in all risk trials, and exercise UI state, comparison snapshots, dialogue resume, imports, both themes and large fonts.

## V41 learning update
Six guided topics include formula meaning, units, worked examples, assumptions, practice feedback and spoken English. Separate deterministic minute-step flow lab conserves released = queues + shipped, has adjustable release/assembly capacity, fixed packing6/min, pause/reset and reduced-motion support. It is a simplified teaching model, not the V20 factory. No new persisted state or XP; existing backups unchanged.
