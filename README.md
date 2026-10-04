# Assignment 3 — Bridge Pattern

**Student:** Niyazov Maksat  
**Group:** SE-2522  
**Topic:** A — Drawing  
**Repository:** https://github.com/DevMax8/bridge-pattern-drawing-java  
**Base Commit:** `278a9bd6ec26f38e5c05a5505ca5f0a0a135f0b4`

## Project Overview

This project demonstrates the **Bridge Design Pattern** using a drawing application.

The application separates two independently varying hierarchies:

- **Abstraction hierarchy:** `Shape` → `Circle`, `Square`
- **Implementation hierarchy:** `Renderer` → `VectorRenderer`, `RasterRenderer`, `AsciiRenderer`

The `Shape` abstraction stores a reference to the `Renderer` interface. Concrete shapes delegate rendering operations through this interface, allowing the abstraction and implementation hierarchies to vary independently.

## Role Map

| Bridge Role | Class | Source Path |
|---|---|---|
| Abstraction | `Shape` | `src/bridge/Shape.java` |
| A1 — Refined Abstraction | `Circle` | `src/bridge/Circle.java` |
| A2 — Refined Abstraction | `Square` | `src/bridge/Square.java` |
| Implementor | `Renderer` | `src/bridge/Renderer.java` |
| I1 — Concrete Implementor | `VectorRenderer` | `src/bridge/VectorRenderer.java` |
| I2 — Concrete Implementor | `RasterRenderer` | `src/bridge/RasterRenderer.java` |
| I3 — Concrete Implementor | `AsciiRenderer` | `src/bridge/AsciiRenderer.java` |
| Client | `Main` | `src/Main.java` |

## Bridge Implementation

### Bridge Field

The interface-typed bridge reference is stored in `Shape`:

```java
protected Renderer renderer;
```

`Shape` depends on the `Renderer` interface rather than on concrete renderer implementations.

### execute()

The abstraction exposes:

```java
public abstract String execute();
```

`Circle` and `Square` implement this operation and delegate rendering through the stored `Renderer`.

### setImplementation()

The implementation can be replaced at runtime using:

```java
public void setImplementation(Renderer renderer);
```

This allows the renderer of an existing shape object to be changed without creating a new shape.

## T5 — Runtime Switch

The runtime-switching demonstration is implemented in the `testRuntimeSwitch()` method in `src/Main.java`.

T5 performs the following checks:

- creates one `Circle` with `VectorRenderer`;
- executes the object;
- preserves its original ID and radius;
- replaces the implementation with `RasterRenderer`;
- executes the same object again;
- verifies object identity using `==`;
- verifies that the ID and radius remain unchanged;
- verifies that the rendering result changes from VECTOR to RASTER.

Expected result:

```text
sameObject=true
stateUnchanged=true
before=VECTOR circle radius=2
after=RASTER circle radius=2
```

## Build and Run

**Requirement:** JDK 17

No external dependencies are required.

Compile from the project root:

```bash
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
```

Run the demonstration:

```bash
java -cp out Main --demo
```

The project can be compiled and executed without an IDE.

## Expected Results

| Test | Combination / Action | Expected Result |
|---|---|---|
| T1 | Circle + VectorRenderer | `VECTOR circle radius=2` |
| T2 | Circle + RasterRenderer | `RASTER circle radius=2` |
| T3 | Square + VectorRenderer | `VECTOR square side=3` |
| T4 | Square + RasterRenderer | `RASTER square side=3` |
| T5 | Circle: VectorRenderer → RasterRenderer | `sameObject=true`, `stateUnchanged=true` |
| T6 | Circle + AsciiRenderer | `ASCII circle radius=2` |
| T7 | Square + AsciiRenderer | `ASCII square side=3` |

Expected summary:

```text
SUMMARY: 7/7 PASS
```

The actual T1–T7 execution results are stored in `demo-output.txt`.

## Independent Extension

The base implementation contains:

- I1 — `VectorRenderer`
- I2 — `RasterRenderer`

The independent extension adds:

- I3 — `AsciiRenderer`

`AsciiRenderer` implements the existing `Renderer` interface.

The existing `Shape`, `Circle`, `Square`, `Renderer`, `VectorRenderer`, and `RasterRenderer` classes do not need to be modified to support the new implementation.

T6 and T7 demonstrate the new implementation with both refined abstractions.

The changes from the base version to the extension are recorded in `extension.diff`.

### Extension Verification

Base commit:

```text
278a9bd6ec26f38e5c05a5505ca5f0a0a135f0b4
```

The extension changes only:

```text
src/Main.java
src/bridge/AsciiRenderer.java
```

The extension diff can be verified with:

```bash
git diff 278a9bd6ec26f38e5c05a5505ca5f0a0a135f0b4 HEAD -- src
```

## Project Structure

```text
src/
├── Main.java
└── bridge/
    ├── Renderer.java
    ├── Shape.java
    ├── Circle.java
    ├── Square.java
    ├── VectorRenderer.java
    ├── RasterRenderer.java
    └── AsciiRenderer.java

sources.txt
README.md
demo-output.txt
extension.diff
```

## Design Pattern Summary

The Bridge Pattern separates an abstraction from its implementation so that both can vary independently.

In this project:

- `Shape` represents the abstraction.
- `Circle` and `Square` are refined abstractions.
- `Renderer` represents the implementor.
- `VectorRenderer`, `RasterRenderer`, and `AsciiRenderer` are concrete implementors.
- `setImplementation()` demonstrates that the implementation can be changed at runtime without replacing the abstraction object.

This design makes it possible to add new renderer implementations without modifying the existing shape hierarchy.