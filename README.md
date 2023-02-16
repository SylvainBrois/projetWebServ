# Projet Web Serveur

<p>BERVOAS Nicolas <br>BROIS Sylvain </p>

### 1. Jeux de données
Nous avons choisi de travailler avec les jeux de données concernant les bières et les brasseries fournies
gratuitement en ligne.
Les données brutes récupérées sont contenues dans des fichiers .csv que nous avons triés via un parser dans notre code.
Le parser lit les documents, sépare les données et crée des objets correspondant à chaque ligne du csv. Le traitement
des données est automatique et se lancera dès l'exécution du programme. Une fois la ligne traitée, un objet en contenant
les informations est fabriqué et est inséré dans notre base de données.  
Le contenu de nos tables est consultable aux adresses suivantes (uniquement si le programme est lancé) : <br>
> Beers : http://localhost:8080/beer  
> Breweries : http://localhost:8080/brewery

Chaque table possède les mêmes opérations CRUD :
- get pour récupérer un élément par son ID
- post pour ajouter un élément à une table
- put pour modifier un élément d'une table par son ID
- delete pour supprimer un élément d'une table par son ID

### 2. Lancement du programme
Pour exécuter le projet, il suffit d'exécuter la fonction main directement via l'IDE, le serveur se lancera et la base
de données se remplira toute seule.

### 3. Tests
Des jeux de tests pour les controllers sont disponibles dans `src/test`.