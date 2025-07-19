# pFLT - Progetto Fondamenti dei Linguaggi e Traduttori
## Anno Accademico 2024-2025

### Autore
**Matteo Schintu** - Matricola: 20051769

---

## 📚 Descrizione del Progetto

Questo progetto implementa un **compilatore completo** per un semplice linguaggio di programmazione imperativo, sviluppato come progetto d'esame per il corso di **Fondamenti dei Linguaggi e Traduttori** presso l'Università del Piemonte Orientale.

Il compilatore è strutturato secondo le fasi classiche di traduzione:
- **Analisi Lessicale** (Scanner)
- **Analisi Sintattica** (Parser) 
- **Analisi Semantica** (Type Checking)
- **Generazione del Codice**

## 🔧 Caratteristiche del Linguaggio

Il linguaggio supporta le seguenti funzionalità:

### Tipi di Dati
- `int` - Numeri interi
- `float` - Numeri in virgola mobile

### Operazioni Supportate
- **Aritmetiche**: `+`, `-`, `*`, `/`
- **Assegnamento**: `=`
- **Output**: `print`

### Sintassi Esempio
```
int x = 10;
float y = 3.14;
int result = x + 5;
print result;
```

## 🏗️ Architettura del Progetto

### Struttura delle Cartelle

```
pFLT/
├── src/                    # Codice sorgente
│   ├── ast/               # Abstract Syntax Tree (AST)
│   ├── scanner/           # Analizzatore lessicale
│   ├── parser/            # Analizzatore sintattico
│   ├── visitor/           # Pattern Visitor per attraversamento AST
│   ├── token/             # Gestione token
│   ├── symbolTable/       # Tabella dei simboli
│   ├── exceptions/        # Eccezioni personalizzate
│   └── test/              # Test e file di esempio
└── bin/                   # File compilati (.class)
```

### Componenti Principali

#### 🔍 **Scanner (Analisi Lessicale)**
- **Classe**: `Scanner.java`
- **Funzione**: Converte il codice sorgente in una sequenza di token
- **Gestisce**: Identificatori, numeri, operatori, parole chiave, caratteri di skip
- **Errori**: Riconosce e segnala errori lessicali

#### 📝 **Parser (Analisi Sintattica)**
- **Classe**: `Parser.java`
- **Funzione**: Costruisce l'Abstract Syntax Tree (AST) dai token
- **Metodo**: Analisi sintattica ricorsiva discendente
- **Output**: Struttura ad albero rappresentante il programma

#### 🌳 **AST (Abstract Syntax Tree)**
Le classi AST rappresentano i costrutti del linguaggio:
- `NodeProgram` - Nodo radice del programma
- `NodeDecl` - Dichiarazioni di variabili
- `NodeAssign` - Assegnamenti
- `NodeBinOp` - Operazioni binarie
- `NodePrint` - Istruzioni di stampa
- `NodeExpr`, `NodeStm` - Espressioni e statement

#### 🔍 **Type Checking (Analisi Semantica)**
- **Classe**: `TypeCheckinVisitor.java`
- **Funzione**: Verifica la correttezza semantica del programma
- **Controlla**: Compatibilità dei tipi, dichiarazioni delle variabili
- **Pattern**: Visitor per attraversare l'AST

#### ⚙️ **Code Generator (Generazione Codice)**
- **Classe**: `CodeGeneratorVisitor.java`
- **Funzione**: Genera codice assembly/macchina dall'AST
- **Gestione**: Registri, istruzioni macchina, ottimizzazioni

#### 🗃️ **Symbol Table (Tabella dei Simboli)**
- **Classe**: `SymbolTable.java`
- **Funzione**: Memorizza informazioni sulle variabili dichiarate
- **Attributi**: Nome, tipo, scope delle variabili

## 🧪 Testing

Il progetto include una suite completa di test:

### Test Scanner
- Riconoscimento token
- Gestione errori lessicali
- Test con identificatori, numeri, operatori

### Test Parser
- Costruzione corretta dell'AST
- Gestione errori sintattici
- Verifica grammatica del linguaggio

### Test Type Checking
- Verifica compatibilità tipi
- Controllo dichiarazioni variabili
- Gestione errori semantici

### Test Code Generator
- Generazione codice corretto
- Gestione registri
- Ottimizzazioni base

## 🚀 Come Eseguire

### Prerequisiti
- Java JDK 8 o superiore
- IDE Java (Eclipse, IntelliJ, VS Code)

### Compilazione
```bash
# Dalla cartella pFLT/src
javac -d ../bin -cp . **/*.java
```

### Esecuzione Test
```bash
# Dalla cartella pFLT/bin
java test.TestScanner
java test.TestParser
java test.TestTypeCheckinVisitor
java test.TestCodeGenerator
```

### Esempio d'Uso
```java
// Creazione scanner dal file sorgente
Scanner scanner = new Scanner("programma.txt");

// Parsing e costruzione AST
Parser parser = new Parser(scanner);
NodeProgram ast = parser.parseProgram();

// Type checking
TypeCheckinVisitor typeChecker = new TypeCheckinVisitor();
ast.accept(typeChecker);

// Generazione codice
CodeGeneratorVisitor codeGen = new CodeGeneratorVisitor();
ast.accept(codeGen);
String codiceGenerato = codeGen.getGenerated();
```

## 📋 Funzionalità Implementate

- ✅ **Analisi Lessicale completa**
  - Riconoscimento token
  - Gestione errori lessicali
  - Skip caratteri non significativi

- ✅ **Analisi Sintattica**
  - Parser ricorsivo discendente
  - Costruzione AST
  - Gestione errori sintattici

- ✅ **Analisi Semantica**
  - Type checking
  - Verifica dichiarazioni
  - Gestione symbol table

- ✅ **Generazione Codice**
  - Traduzione in linguaggio macchina
  - Gestione registri
  - Ottimizzazioni base

- ✅ **Gestione Errori**
  - Eccezioni personalizzate
  - Messaggi d'errore informativi
  - Recovery da errori

## 🎯 Obiettivi Didattici

Questo progetto dimostra la comprensione di:

1. **Teoria dei Compilatori**
   - Fasi di traduzione
   - Grammatiche formali
   - Analisi sintattica

2. **Implementazione Pratica**
   - Pattern Visitor
   - Gestione della memoria
   - Architettura modulare

3. **Ingegneria del Software**
   - Testing sistematico
   - Gestione errori
   - Documentazione del codice

## 📚 Riferimenti

- **Corso**: Fondamenti dei Linguaggi e Traduttori
- **Università**: Università del Piemonte Orientale
- **Anno Accademico**: 2024-2025
- **Linguaggio**: Java

## 📝 Note Tecniche

- **Paradigma**: Analisi ricorsiva discendente
- **Pattern**: Visitor per attraversamento AST
- **Architettura**: Modulare con separazione delle responsabilità
- **Testing**: Unit test per ogni componente
- **Documentazione**: Javadoc per tutte le classi pubbliche

---

*Progetto sviluppato per il corso di Fondamenti dei Linguaggi e Traduttori - UPO 2024-2025*
