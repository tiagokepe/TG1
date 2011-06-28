install: dissertacao.ps


dissertacao.ps: dissertacao.dvi
	dvips -o dissertacao.ps dissertacao.dvi


clean: 
	rm -f *.aux
	rm -f *.bbl
	rm -f *.bak
	rm -f *.log
	rm -f *.blg 
	rm -f *.toc
	rm -f *.lot
	rm -f *.lof
	rm -f *.dvi
	rm -f *.idx 
	rm -f *.ilg 
	rm -f *.ind

dissertacao.dvi: dissertacao.tex capitulo1.tex luciano.bbl
	latex dissertacao
	latex dissertacao

luciano.bbl: luciano.bib
	latex dissertacao
	bibtex dissertacao

