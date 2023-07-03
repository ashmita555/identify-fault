package com.identifyfault.main;

import com.identifyfault.enums.FaultTypeEnum;

import java.io.*;
import java.util.*;

/**
 * @author ashmita.tandon on 01/07/23
 */
public class IdentifyFaultApplication {
    public static void main(String []args){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("circuitfile.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        Map<String, String> circuitOperations = new LinkedHashMap<>();
        try{
            while(bufferedReader.ready()){
                String line = bufferedReader.readLine();
                String [] arr = line.split("=");
                circuitOperations.put(arr[0], arr[1]);
            }
        } catch (IOException e){
            System.out.println("Error occurred: " + e.getMessage());
        }
        //Getting input from user
        Scanner sc =  new Scanner(System.in);
        String faultAt = sc.nextLine();
        String faultType = sc.nextLine();
        sc.close();
        String [] faultAtArr = faultAt.replaceAll("\\s","").split("=");
        String faultyNode = faultAtArr[1];
        String []faultTypeArr = faultType.replaceAll("\\s","").split("=");
        FaultTypeEnum faultTypeEnum = FaultTypeEnum.getByName(faultTypeArr[1]);
        //Get input binary (A,B,C,D)
        ArrayList<String> binaryInputList = getBinaryNumbers(16);
        for(String input : binaryInputList){
            String output1 = solveCircuit(circuitOperations, input, faultyNode, faultTypeEnum, false);
            String output2 = solveCircuit(circuitOperations, input, faultyNode, faultTypeEnum, true);
            if(!output1.equalsIgnoreCase(output2)){
                StringBuilder programOutput = new StringBuilder();
                programOutput.append("[A,B,C,D] = [")
                        .append(input.charAt(0))
                        .append(",")
                        .append(input.charAt(1))
                        .append(",")
                        .append(input.charAt(2))
                        .append(",")
                        .append(input.charAt(3))
                        .append("],Z = ")
                        .append(output2);
                System.out.println(programOutput.toString());
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("src/main/resources/output.txt", "UTF-8");
                    writer.println(programOutput.toString());
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    private static String solveCircuit(Map<String, String> circuitOperations, String binaryInput, String faultyNode, FaultTypeEnum faultTypeEnum, boolean checkFaultOutput){
        Map<String, String> results = new HashMap<>();
        results.put("A", String.valueOf(binaryInput.charAt(0)));
        results.put("B", String.valueOf(binaryInput.charAt(1)));
        results.put("C", String.valueOf(binaryInput.charAt(2)));
        results.put("D", String.valueOf(binaryInput.charAt(3)));
        if(checkFaultOutput)
            results.put(faultyNode, faultTypeEnum.getValue());
        for(Map.Entry<String, String> entry : circuitOperations.entrySet()){
            String operator = getOperator(entry.getValue());
            String variables [] = entry.getValue().split("[&|~^]");
            String expressionResult;
            if(checkFaultOutput && entry.getKey().equalsIgnoreCase(faultyNode)){
                continue;
            }
            else if(operator=="~")
                expressionResult = solveOperation(results.get(variables[1]), null, operator);
            else
                expressionResult =  solveOperation(results.get(variables[0]), results.get(variables[1]), operator);
            results.put(entry.getKey(), expressionResult);
        }
        return results.get("Z");
    }

    private static String getOperator(String expression){
        if(expression.contains("&"))
            return "&";
        else if(expression.contains("|"))
            return "|";
        else if(expression.contains("~"))
            return "~";
        else if(expression.contains("^"))
            return "^";
        return null;
    }

    private static String solveOperation(String input1, String input2, String operator){
        int result = 0;
        switch (operator){
            case "&":
                result =  Integer.valueOf(input1)&Integer.valueOf(input2);
                break;
            case "|":
                result =  Integer.valueOf(input1)|Integer.valueOf(input2);
                break;
            case "^":
                result = Integer.valueOf(input1)^Integer.valueOf(input2);
                break;
            case "~":
                if(input1.equalsIgnoreCase("0"))
                    result = 1;
                else result = 0;
        }
        return String.valueOf(result);
    }


    private static ArrayList<String> getBinaryNumbers(int n){
        ArrayList<String> binaryNumbers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String str = "";
            int temp = i;
            while (temp != 0) {
                if ((temp & 1) == 1) {
                    str = "1" + str;
                } else {
                    str = "0" + str;
                }
                temp = temp >> 1;
            }
            if(str.length() < 4){
                str = paddingZero(str);
            }
            binaryNumbers.add(str);
        }
        return binaryNumbers;
    }

    private static String paddingZero(String binaryNumber){
        int length = binaryNumber.length();
        for(int i=0; i < 4-length; i++){
            binaryNumber= "0" + binaryNumber;
        }
        return binaryNumber;
    }
}
