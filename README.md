[![Typing SVG](https://readme-typing-svg.demolab.com?font=Fira+Code&size=25&pause=1000&color=F74D29&center=true&width=435&lines=Coding+Week+-+groupe+28;Coding+Week+-+groupe+28;%F0%9F%AA%B6%F0%9F%AA%B6%F0%9F%AA%B6)](https://git.io/typing-svg)

---

## Membres du groupe :
- Valentin Chanel (gpe. 31, IL)
- Nicolas Frache (gpe. 41, IL)
- Théo Goureau (gpe. 41, IL)
- Cyrielle Lacrampe--Diter (gpe. 42, IL)

---
### 📖 [Sujet du projet](./CodingWeek%202022-2023%20-%20Sujet.pdf)
---


## Comment lancer l'application :

▶️ Double cliquer sur le fatjar dans le dossier racine

Une pile est disponible pour tester l'application à l'adresse suivante : https://drive.google.com/file/d/1wzGIJ0KSpzcUO2OJtanszYdl4KkEs96Z/view?usp=share_link

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
```antlrv4
cd PROJET
```
---

### Utiliser les goals

- Nettoyer le projet

``` antlrv4
mvn clean
```

--- 
- Générer le lexer et le parser puis compiler

``` antlrv4
mvn compile
```

---

- lance les tests (grace au plugin *surefire*) + tâches précèdentes si nécessaires
``` antlrv4
mvn test
```

