[![Typing SVG](https://readme-typing-svg.demolab.com?font=Fira+Code&size=25&pause=1000&color=F74D29&center=true&width=435&lines=Coding+Week+-+groupe+28;Coding+Week+-+groupe+28;%F0%9F%AA%B6%F0%9F%AA%B6%F0%9F%AA%B6)](https://git.io/typing-svg)

---
# PROJET
## Membres du groupe :
- Valentin Chanel (gpe. 31, IL)
- Nicolas Frache (gpe. 41, IL)
- Théo Goureau (gpe. 41, IL)
- Cyrielle Lacrampe--Diter (gpe. 42, IL)

## Liens :
### 📃 [Sujet du projet](./CodingWeek%202022-2023%20-%20Sujet.pdf)
### 📖 [Rapport du projet](./rapport_coding_week-groupe28.pdf)
### 📹 [Vidéo de présentation de l'application](https://youtu.be/XnKOJWRih6U)
---
# L'APPLICATION
## Requirments
### Pour lancer le projet
Utilisez Java 19

_Ubuntu_ : ```sudo apt install openjdk-19-jdk```

_Windows_ : https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html
### Pour compiler le projet
Maven s'occupe de tout ! Si vous aimez vraiment le travail manuel, vous trouverez les dépendances dans [le pom](flashcards/pom.xml).

## Comment lancer l'application :

▶️ Double cliquer sur le fatjar dans le dossier racine

Des piles sont disponibles pour tester l'application [ici](https://drive.google.com/file/d/1XGKdXPhnFriDjxxYV2lBWlwp9bic4QeT/view?usp=sharing) (test des capitales) et [là](https://drive.google.com/file/d/13iMRbPVeOHG3UGYa4ajhLX3yHgdtg6R7/view?usp=sharing) (test des citations célèbres).

## Comment utiliser maven :

---
# MAVEN
## Utilisation de Maven
### Installer Maven
_Ubuntu_ : ```sudo apt install Maven```

_Windows_ : https://maven.apache.org/install.html#windows-tips
    
    ▶️ Ou utiliser les packages et extension MAVEN d'un IDE (beaucoup plus pratiques)

---
### Se déplacer dans le bon projet
```bash
cd flashcards
```
---

### Utiliser les goals

- Nettoyer le projet

```bash
mvn clean
```

- Lancer l'application 

```bash
mvn javafx:run
```

- Créer le Fat Jar (les tests sont réalisés avant de créer le Fat Jar) : on le retrouve dans target
```bash
mvn package
```

---