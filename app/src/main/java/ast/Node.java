package ast;

import cal.Visitor;
import field.Field;

public interface Node {

    int capacity();

    void attachChild(Node child);

    Field accept(Visitor v);

}
