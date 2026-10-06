# Software: NEST Software — Doctor-Facing SELP Ordering & Injection Assistant

## Abstract

We are building **NEST Software**, a doctor-facing software platform that
bridges the gap between our engineered **silk-elastin-like protein (SELP)**
biomaterials and clinical day-to-day use. The platform comprises two tightly
integrated modules:

1. **Ordering Module** – a catalogue front end that lets clinicians select
   preconditioned SELP scaffolds together with the required *functional
   modules* (e.g. the mitochondrial therapy module), query a remote server for
   the matching physico-chemical properties (mechanical stiffness, degradation
   profile, gelation kinetics, etc.), and place an order.
2. **Injection Module** – a bedside workflow that starts by scanning the
   **QR code printed on the formulation packaging** to load the
   product-specific manual of use, guides the clinician through an ordered
   **reagent loading** sequence, and then gates **premixing** on the hardware
   green-light signal before the formulation is ready for injection.

The software is not a control-panel afterthought: it is the interface through
which our material design (PBS–SELP–Ru–sPS modular chemistry) becomes an
actionable, traceable, and safe clinical workflow.

---

## 1. Description

Our project centres on injectable **SELP** hydrogels for therapeutic delivery.
SELP (silk-elastin-like protein) combines the strength of silk with the
elasticity of elastin and, together with a spider-silk (sPS) mechanical
backbone, a ruthenium-based (Ru) photo-crosslinking unit and a PBS buffering
phase, forms a modular, tunable, injectable material system.

A material system this modular creates a real usability problem for the
clinician: *which scaffold do I pick, what are its physical limits, and how do
I prepare it correctly?* This is exactly where software belongs.

The software delivers three promises to the end user (the doctor):

- **Correct selection.** Preconditioned scaffold + functional module
  combinations are presented explicitly; the server returns truly measured
  physical properties, keyed to the batch identifier.
- **Correct preparation.** The workflow is locked step by step: reagents are
  loaded in a defined order with explicit per-reagent confirmation, and the
  hardware state (premixer green light), not human assumption, gates readiness.
- **Traceability.** Every scanned batch and confirmed checklist item is
  recorded, tying material identity to patient-side action.

---

## 2. Why Software? (Problem Statement)

- Injectable biomaterials are increasingly delivered as "kits" assembled from
  interchangeable parts; their safe clinical use depends on the operator
  executing a multi-step protocol correctly and in the right order.
- Paper manuals are formulation-generic: they cannot adapt to the exact
  scaffold / functional-module lot in the clinician's hands.
- Physical preparation (loading, premixing) requires temporal coordination
  between the operator and electromechanical hardware.
- There is no existing open, doctor-facing interface that turns a
  paracrine-embedded hydrogel kit into a guided, verifiable procedure.

NEST Software closes each of these gaps by making the *information* a
first-class part of the material kit itself (QR code on the formulation) and
by making the *hardware* part of the software loop (state-aware workflow).

---

## 3. Ordering Module

The ordering module is the clinician's entry point into the material system.

- The clinician browses **preconditioned SELP scaffolds**.
- For each scaffold the clinician can attach one or more **functional
  modules**. Functional modules are wet-lab products of separate project
  tracks and are linked from the catalogue via in-app hyperlinks to their
  dedicated wiki pages — for example the **mitochondrial therapy module**
  ([link inserted here by the wet-lab team]).
- On selection, the client **queries the remote material server** and
  retrieves the **physico-chemical properties** associated with the exact
  chosen combination, including:
  - compressive / shear mechanical properties,
  - predicted degradation profile,
  - gelation kinetics and injection window,
  - recommended crosslinking (blue-light / Ru) parameters.
- The displayed values are server-authoritative (from characterised lots) and
  rendered together with the batch identifier, so "what you order" and "what
  you read on the label" are the same data.

**Figure 1 (insert here):** Ordering module – scaffold selector screen.

**Figure 2 (insert here):** Ordering module – functional module selection and
linked property panel returned by the remote server.

---

## 4. Injection Module

The injection module is a step-wise clinical wizard run on the doctor's
tablet / phone. It is the module that is prototyped and exercised in this
project's living demo. The wizard currently guides the clinician through
**four steps**:

```
Scan QR → General Instructions → Add Reagents → Premixing
```

### 4.1 Scan the formulation code

Every dispensed syringe / vial carries a **printed QR code**. Scanning it loads
the *formulation-specific* manual of use (component composition, handling
conditions, safety notes, storage & QC data) directly onto the device. A fully
curated, non-placeholder manual is rendered for the recognised product.

**Figure 3 (insert here):** Injection module – QR scan step with the
on-screen recognition frame.

**Figure 4 (insert here):** Injection module – the formulation-specific
manual loaded after scanning.

### 4.2 Reagent loading in order

The formulation is assembled from its modular constituents in a **fixed
sequence**: **PBS → SELP → Ru → sPS → functional module**. Each reagent is
presented as a progress card; the clinician places one reagent and then taps
its card to confirm. The next card unlocks only after the previous one is
confirmed, and the wizard does not advance until **all** reagents are loaded.

**Figure 5 (insert here):** Injection module – ordered reagent-loading cards
(PBS, SELP, Ru, sPS, functional module).

### 4.3 Premixing gated by hardware

Once all reagents are loaded, the software hands control to the **premixer**.
The premise is explicit in the instruction: *wait for the premixer's green
light*. The step completes only when the hardware returns a **nominal signal**
(green indicator / healthy state) — the software does not let the operator
assume readiness.

**Figure 6 (insert here):** Injection module – premixing instruction relying
on the hardware green-light signal.

---

## 5. Software Architecture

### 5.1 Client (Android app)

- **Language / framework:** Kotlin, Jetpack Compose (Material 3), single-page
  modular design.
- **i18n:** the whole user interface is localised in **Chinese and English**
  and can be toggled in-app (EN/CH).
- **QR scanning:** camera-backed capture (CameraX) with on-device decoding
  (ZXing); an explicit manual-entry fallback keeps the workflow usable where a
  camera is not available.
- **Wizard engine:** a four-step state machine
  (Scan → Instructions → Reagents → Premix). The reagent progression is
  tracked as an ordered sequence with a per-reagent confirmation gate.

### 5.2 Server

- The ordering module depends on a **remote material server** that returns
  physico-chemical properties keyed by scaffold + functional-module
  combination and batch identifier.
- The server, together with the QR payload, is the single source of truth for
  formulation identity, so app and label cannot drift apart.

### 5.3 Data flow

```
[QR on packaging] → [Scanner (CameraX + ZXing)] → [formulation ID]
   → [manual rendered] → [ordered reagent loading (PBS→SELP→Ru→sPS→module)]
   → [premix: wait for green light] → [ready for injection]

Ordering UI → [select scaffold + modules] → [server API] → [properties]
```

**Figure 7 (insert here):** architecture / data-flow diagram.

---

## 6. Hardware Integration

The software drives the electromechanical chain of the project:

- **Reagent loading** is confirmed per constituent through the ordered cards;
- **Premixing** is started and acknowledged through the hardware green-light
  signal;
- readiness for injection is reached only after the premixer reports a
  healthy state (no guesswork).

**Figure 8 (insert here):** hardware–software state diagram
(load → premix(green) → nominal → ready for injection).

---

## 7. Safety & Usability

- Ordered reagent loading prevents mis-sequenced or incomplete formulation.
- Premixing is gated behind a hardware green-light signal (no assumptions).
- Large-format status text is designed to be read at a glance during a
  hands-busy procedure.
- All copy is provided in both Chinese and English, and ships free of
  scaffold/test strings — the app reads as a product, not a prototype.

---

## 8. Future Work

- **Offline mode & queueing:** cache server responses per batch to make the
  ordering module usable in low-connectivity clinical environments.
- **Electronic batch records (EBR):** persist scanned IDs and confirmations
  into an auditable log (CSV/JSON export).
- **Web companion:** read-only server-rendered property sheets linked from
  functional-module pages.
- **Multi-language manuals:** render QR-payload manuals from server-side
  localisation tables.

---

## 9. Wiki Integration

- **Ordering module** links out to wet-lab pages for each functional module
  (e.g. mitochondrial therapy module) as in-app hyperlinks.
- **Experimental characterisation** (mechanical, degradation, gelation) is
  shared with the corresponding Experiments / Engineering pages of this wiki.

---

## 10. Contributors & Acknowledgements

Team members responsible for the software track (names/roles to be inserted).

**Figure 9 (insert here):** project team / software track credits or team
photo.