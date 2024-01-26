   
import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;
import java.lang.Math;
   
public class Main
{
    static final int INTEGER_LIMIT = (int)Math.pow(2, 15) - 1;

    static public void main(String argv[])
    {
        Lexer l;
        Symbol s;
        FileReader file_reader;
        PrintWriter file_writer;
        String inputFilename = argv[0];
        String outputFilename = argv[1];
        
        try
        {
            /********************************/
            /* [1] Initialize a file reader */
            /********************************/
            file_reader = new FileReader(inputFilename);

            /********************************/
            /* [2] Initialize a file writer */
            /********************************/
            file_writer = new PrintWriter(outputFilename);
            
            /******************************/
            /* [3] Initialize a new lexer */
            /******************************/
            l = new Lexer(file_reader);

            /***********************/
            /* [4] Read next token */
            /***********************/
            try
            {
                s = l.next_token();
                if (s.sym == TokenNames.INT && ((int)s.value < -INTEGER_LIMIT || (int)s.value > INTEGER_LIMIT))
                    throw new NumberFormatException();
            }
            catch (NumberFormatException e)
            {
                s = new Symbol(TokenNames.ERROR, l.getLine() - 1, l.getTokenStartPosition() - 1);
            }

            /********************************/
            /* [5] Main reading tokens loop */
            /********************************/
            while (s.sym != TokenNames.EOF && s.sym != TokenNames.ERROR)
            {
                /************************/
                /* [6] Print to console */
                /************************/
                
                System.out.print(getSymbolName(s));
                System.out.print("[");
                System.out.print(l.getLine());
                System.out.print(",");
                System.out.print(l.getTokenStartPosition());
                System.out.print("]");
                System.out.print("\n");
                
                /*********************/
                /* [7] Print to file */
                /*********************/
                
                file_writer.print(getSymbolName(s));
                file_writer.print("[");
                file_writer.print(l.getLine());
                file_writer.print(",");
                file_writer.print(l.getTokenStartPosition());
                file_writer.print("]");
                file_writer.print("\n");
                
                /***********************/
                /* [8] Read next token */
                /***********************/
                try
                {
                    s = l.next_token();
                    if (s.sym == TokenNames.INT && ((int)s.value < -INTEGER_LIMIT || (int)s.value > INTEGER_LIMIT))
                        throw new NumberFormatException();
                }
                catch (NumberFormatException e)
                {
                    s = new Symbol(TokenNames.ERROR, l.getLine() - 1, l.getTokenStartPosition() - 1);
                }
            }

            if (s.sym == TokenNames.ERROR)
            {
                System.out.print("ERROR");
                System.out.print("[");
                System.out.print(l.getLine());
                System.out.print(",");
                System.out.print(l.getTokenStartPosition());
                System.out.print("]");
                System.out.print("\n");
            }
            
            /******************************/
            /* [9] Close lexer input file */
            /******************************/
            l.yyclose();

            /**************************/
            /* [10] Close output file */
            /**************************/
            file_writer.close();

            if (s.sym == TokenNames.ERROR)
            {
                file_writer = new PrintWriter(outputFilename);
                file_writer.print("ERROR");
                file_writer.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    static public String getSymbolName(Symbol s)
    {
        switch(s.sym)
        {
            case TokenNames.PLUS:
                return "PLUS";
            case TokenNames.MINUS:
                return "MINUS";
            case TokenNames.TIMES:
                return "TIMES";
            case TokenNames.DIVIDE:
                return "DIVIDE";
            case TokenNames.LPAREN:
                return "LPAREN";
            case TokenNames.RPAREN:
                return "RPAREN";
            case TokenNames.INT:
                return "INT(" + s.value + ")";
            case TokenNames.ID:
                return "ID(" + s.value + ")";
            case TokenNames.CLASS:
                return "CLASS";
            case TokenNames.ARRAY:
                return "ARRAY";
            case TokenNames.EXTENDS:
                return "EXTENDS";
            case TokenNames.RETURN:
                return "RETURN";
            case TokenNames.WHILE:
                return "WHILE";
            case TokenNames.IF:
                return "IF";
            case TokenNames.NEW:
                return "NEW";
            case TokenNames.STRING:
                return "STRING(" + s.value + ")";
            case TokenNames.TYPE_INT:
                return "TYPE_INT";
            case TokenNames.TYPE_STRING:
                return "TYPE_STRING";
            case TokenNames.LBRACK:
                return "LBRACK";
            case TokenNames.RBRACK:
                return "RBRACK";
            case TokenNames.LBRACE:
                return "LBRACE";
            case TokenNames.RBRACE:
                return "RBRACE";
            case TokenNames.NIL:
                return "NIL";
            case TokenNames.COMMA:
                return "COMMA";
            case TokenNames.DOT:
                return "DOT";
            case TokenNames.SEMICOLON:
                return "SEMICOLON";
            case TokenNames.TYPE_VOID:
                return "TYPE_VOID";
            case TokenNames.ASSIGN:
                return "ASSIGN";
            case TokenNames.EQ:
                return "EQ";
            case TokenNames.LT:
                return "LT";
            case TokenNames.GT:
                return "GT";
        }
        return "ERROR";
    }
}