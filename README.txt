Salvatore Dipace - Gestione Magazzino Lego

Istruzioni per l'installazione e l'utilizzo dell'applicazione

1 - Fork del progetto (https://github.com/TdP-prove-finali/DipaceSalvatore);

2 - Caricare con HeidiSQL i file per la base dati nel seguente ordine
	2.1	eseguire le istruzioni del file lego_v6.sql per creare le tabelle e poolare la tabella themes
	2.2 per velocizzare le configurazioni importare i file .csv nel seguente ordine ignorando gli avvisi di errore:
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
			
     2.3 in alternativa lanciare le istruzioni del file lego_completo.sql
    
			
3 - Cambiare l'utente e la password nella classe DBConnect

4 - Eseguire la classe Main;

5 - Leggere la relazione tecnica;

6 -	Vedere il video dimostrativo https://youtu.be/JzkvN3o1dbM