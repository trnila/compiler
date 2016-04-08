JFLAGS = -g -d classes
JC = javac
CLASSES = \
 	classes/JavaCharStream.class \
	classes/LangParser.class \
	classes/LangParserConstants.class \
	classes/LangParserTokenManager.class \
	classes/ParseException.class

all: JavaCharStream.java classes

JavaCharStream.java: lang.jj
	javacc lang.jj

classes: $(CLASSES)
classes/JavaCharStream.class: JavaCharStream.java
	$(JC) $(JFLAGS) $^
classes/LangParser.class: LangParser.java
	$(JC) $(JFLAGS) $^
classes/LangParserConstants.class: LangParserConstants.java
	$(JC) $(JFLAGS) $^
classes/LangParserTokenManager.class: LangParserTokenManager.java
	$(JC) $(JFLAGS) $^
classes/ParseException.class: ParseException.java
	$(JC) $(JFLAGS) $^

clean:
	$(RM) *.java classes/*.class

test: all
	java -cp classes LangParser tests/exprs
	java -cp classes LangParser tests/t1.txt
	java -cp classes LangParser tests/t2.txt
	java -cp classes LangParser tests/t3.txt
