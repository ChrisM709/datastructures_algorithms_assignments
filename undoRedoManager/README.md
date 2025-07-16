# Undo / Redo Manager Demo (Java)

A **console application** that demonstrates a tiny undo-redo system
backed by a doubly-linked list.

```
Menu.java          ── user-facing menu loop  
MenuFunctions.java ── business logic (menu items + snapshots)  
UndoRedoManager.java ── generic timeline that stores snapshots
```

## Features

* Add unlimited menu items.
* **Undo** steps one change back.
* **Redo** steps forward again (until you add a new item, which clears the redo branch).
* History storage is **generic** – switch from `List<String>` to any object type by changing one line.

## How it works (1-minute dive)

```
UndoRedoManager<Snapshot>
  └─ push(Snapshot)   ← save a new copy of state
  └─ undo()           ← move cursor to previous node
  └─ redo()           ← move cursor to next node
```

`MenuFunctions` holds the *live* `List<String>` and mirrors it to / from the
manager whenever you add, undo, or redo.  
`Menu` is just the CLI wrapper.

## Requirements

* JDK 17 (or any recent LTS)
* A terminal / VS Code with the Java extension

## Compile & Run

```bash
# from the project root
javac *.java
java Menu
```

## Quick Demo

```
--- MENU ---
1. View current menu
2. Add an item
3. Undo last change
4. Redo
5. Exit
Choice: 2
Enter new item: Soup

Choice: 2
Enter new item: Salad

Choice: 1
Current items: [Soup, Salad]

Choice: 3   # undo
Choice: 1
Current items: [Soup]

Choice: 4   # redo
Choice: 1
Current items: [Soup, Salad]
```
