# Problem Statement
The task is to design an algorithm and write its code to identify the input vector required to identify the fault at a given node in a given circuit.
In such a case, there would only be a single fault in the design.
The issue arises from the fact that manufactured chips may contain structural flaws that must be checked before being given to end users. The algorithm needs to be effective, reliable, and quick to spot errors.
The algorithm requires inputs like the circuit file, the position of the fault node, and the fault type (SA0 or SA1). Four inputs (A, B, C, and D) and one output (Z) are specified for the circuit utilizing boolean logic gates (AND, OR, NOT, and XOR). The objective is to identify the input vector at the designated node that causes the fault, and then to verify the fault by looking at the predicted value of the output.
The solution to this problem benefits chip manufacturers, who need to ensure the quality and reliability of their products. By accurately identifying faults in the circuit, manufacturers can perform targeted testing and debugging, leading to improved chip performance and customer satisfaction.

# Summary 
The problem statement is to design an algorithm to identify faults in manufactured chips by analyzing a given circuit.  The algorithm needs to determine the input vector that triggers a fault at a specific node in the circuit. The solution involves parsing the circuit file, applying boolean logic operations, and systematically testing different input combinations to identify the fault. The algorithm should be efficient, robust, and capable of quickly identifying faults. The output of the algorithm includes the input vector that triggers the fault and the expected output value for confirmation. This solution is essential for chip manufacturers to ensure the quality and reliability of their products before delivering them to end users.

