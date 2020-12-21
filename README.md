# Proyecto MultiAgente Covid19

![Alt text](https://ichef.bbci.co.uk/news/800/cpsprodpb/FBA1/production/_111471446_virus.jpg "Proyecto Multiagente Covid19") 

Proyecto de la asignatura Agentes y Sistemas Multiagente del Máster en
Ciencia y Tecnología Informática de la Universidad Carlos III de Madrid.

La finalidad de este proyecto consiste en **simular mediante un sistema de
agentes la propagación de la COVID19** considerando distintas situaciones
y entidades con las que acercarse a la realidad lo máximo posible.

## Introducción 

La situación generada por el COVID-19 ha cambiado nuestras vidas indudablemente. Estamos ante una situación inaudita, donde la cantidad de interacciones que tienen los ciudadanos influye directamente en su probabilidad de contagio y, por tanto, en su posterior incapacidad (y la de sus convivientes) para realizar su actividad normal debido a la enfermedad. 

Por ello, es importante realizar estudios demográficos para aproximar comportamientos futuros. Esto permitiría a las autoridades competentes a realizar restricciones necesarias que permitan más movilidad, sin tener que poner en riesgo a un ya afectado sistema sanitario.

En este proyecto, se intenta modelar una ciudad y el comportamiento de 15 ciudadanos ante una hipótesis de un comportamiento basado en “4 burbujas amigas”. Este concepto de las “burbujas amigas”, aplicado durante el post confinamiento en alemania, se basa en limitar la interacción de una burbuja conviviente a N otras burbujas convivientes.

Nuestra hipótesis es que esto permitiría que la sociedad siguiera socializando, realizando actividades tanto educativas/laborales como lúdicas (bar, deportes, parque...), pero disminuyendo su probabilidad de contagio. Esto es debido a que su probabilidad de contagio real está en las interacciones con sus “burbujas amigas”.

En esta simulación se han modelado 6 tipos de estancia (5 homes, un parque, un bar, una instalación deportiva, una escuela, un trabajo y un hospital), dos tipos de agente (young y adult) y tres grados de responsabilidad de los agentes. Los agentes young harán distintas subrutinas en función de si son niños (r1 y r2) o jóvenes estudiantes universitarios (r3), y los adults en función de si son laboralmente activos (r1 y r2) o jubilados (r3). La responsabilidad afectará a los días que un agente pasa inactivo (en el hospital) hasta que deja de tener síntomas (low responsible y medium responsible) pasando a ser agentes que pueden infectar, o realiza la cuarentena obligatoria (high responsible) y no pueden contagiar más.


## Planteamiento de la solución

Para la ejecución del problema se tendrán en cuenta los distintos factores:

* 2 tipos de Agentes
    * Young people:
        * Probabilidad de ser asintomático alta
        * Probabilidad de complicaciones bajo
    * Adult people:
        * Probabilidad de ser asintomático baja
        * Probabilidad de complicaciones alto

* 4 estancias:
    * Bar: Agentes Young se quitarán la mascarilla, mientras agentes Old se la dejarán puesta
    * Work: Misma probabilidad de contagio para todos los agentes
    * Hospital: Los agentes contagiados irán a la casilla Hospital en caso de enfermar
    * Home: Probabilidad de contagio 0 (todos los agentes viven en casas distintas)
    
## Esquema de la solución propuesta

En la siguiente imagen veremos un esquema del output que proporcionará el sistema:

<p align="center">
  <img src="/images/screen.png" width="350" alt="accessibility text">
</p>
