Salvatore Dipace - Gestione Magazzino Lego

Istruzioni per l'installazione e l'utilizzo dell'applicazione

1 - Fork del progetto (https://github.com/TdP-prove-finali/DipaceSalvatore);

2 - Caricare con HeidiSQL i file per la base dati nel seguente ordine
	2.1	eseguire le istruzioni del file lego_v5.sql per creare le tabelle
	2.2 eseguire le istruzioni del file themes.sql
	2.3 importare i file .csv nel seguente ordine:
			colors.csv
			part_categories.csv
			minifigs.csv
			parts.csv
			elements.csv
			part_relationships.csv
			sets.csv
			inventories.csv
			inventory_parts.csv
			inventory_minifigs.csv
			inventory_sets.csv
			
3 - Cambiare l'utente e la password nella classe DBConnect

4 - Eseguire la classe Main;

5 - Leggere la relazione tecnica;

6 -	Vedere il video dimostrativo https://youtu.be/JzkvN3o1dbM