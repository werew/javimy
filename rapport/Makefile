TARGET=rapport

all: $(TARGET).ps
	ps2pdf $(TARGET).ps

$(TARGET).ps: $(TARGET).dvi
	dvips -t a4 $(TARGET).dvi

$(TARGET).dvi: $(TARGET).tex
	latex $(TARGET).tex
	bibtex $(TARGET).aux
	latex $(TARGET).tex
	latex $(TARGET).tex

clean:
	 rm -f $(TARGET).aux $(TARGET).bbl $(TARGET).blg $(TARGET).dvi $(TARGET).log $(TARGET).toc $(TARGET).ps page-garde.aux
