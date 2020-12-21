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


## Modelo World

El modelo del mundo usado en esta simulación es un grid 50x50. En este modelo actuarán 15 agentes (10 young y 5 adult), con distintas subrutinas en función de su edad. 

El modelo también tendrá las siguientes localizaciones:
- HomeX: Cada agente volverá a su correspondiente casa una vez acabado el día, y empezará siempre su rutina desde ahí a no ser que esté infectado y salga del hospital.
- Hospital: Agentes infectados con síntomas irán al hospital a tratarse. Aquellos agentes de baja responsabilidad saldrán del hospital una vez ya no tengan síntomas. Esto nos permitirá modelar aquellos ciudadanos que no realizan la cuarentena de 14 días obligatoria y, por tanto, pueden seguir infectando a otros agentes.
- Bar: Casilla de simulación de una interacción en un bar. Es el gran foco de contagio, debido a la propensión a quitarse la mascarilla. 
- School: Casilla de simulación de un colegio, donde los young (tanto niños como jóvenes) realizan sus planes de lunes a viernes, además de otras actividades lúdicas.
- Job: Casilla de simulación del trabajo, donde los adult y young universitarios realizan sus interacciones durante la semana, además de otras actividades lúdicas.
- Sports: Casilla de simulación de una instalación deportiva.
- Park: Casilla de simulación de un parque público.

<p align="center">
  <img src="/images/screen.png" width="500" alt="accessibility text">
</p>

    
## Modelo de Infección

Dentro de este proyecto se ha implementado un modelo simplificado de infección por COVID-19 que considera distintos tipos de enfermos así como la posibilidad de contagio en función de los agentes con capacidad de contagio en un determinado lugar público. Además también se ha considerado la posibilidad de contagio dentro del ambiente familiar (referido a los agentes que pertenecen a un mismo home).

### Agentes Contagiosos
Para modelar los agentes con capacidad de contagio, hemos considerado dos casos, agentes infectados y agentes asintomáticos:

- Agentes Infectados: pueden pertenecer tanto al colectivo de agentes jóvenes como al de adultos y se pueden infectar en el ambiente familiar o en espacios públicos. Al día siguiente de producirse la infección estos agentes acudirán de manera automática al hospital, de donde, después de un periodo variable de días, saldrán curados. Cabe destacar que en función de la responsabilidad de estos agentes saldrán con capacidad de contagiar (como asintomáticos) o no:
    - Agentes con baja responsabilidad: pasarán dos días en el hospital, tendrán capacidad de contagiar hasta tres días después.
    - Agentes con media responsabilidad: pasarán cuatro días en el hospital, tendrán capacidad de contagiar hasta dos días después.
    - Agentes con alta responsabilidad: pasarán cinco días en el hospital, no tendrán capacidad de contagiar cuando salgan.

- Agentes Asintomáticos: únicamente pueden pertenecer al colectivo de agentes jóvenes y pueden infectar tanto en el ambiente familiar como en el público. Estos agentes nunca acuden al hospital y salen de casa a no ser que se encuentren en cuarentena. Dejan de ser contagiosos tras un periodo fijado de días. Un agente puede convertirse en asintomático al salir del hospital antes de tiempo o al contagiarse.



### Cuarentenas
Dentro de una unidad familiar, que comprende a todos los agentes que pertenecen a una misma casa, se ha implementado el concepto de cuarentenas. Este estado es incompatible con estar infectado (porque se dirige al hospital) pero no con la capacidad de infección (asintomáticos). Un agente que está en cuarentena (tiene esa creencia) no realiza ninguna de las rutinas establecidas, no saliendo del domicilio, y tampoco tiene capacidad para infectar o ser infectado en el ámbito privado (se considera que está aislado).

Cuando un agente infectado (no asintomático) acude al hospital, realiza un broadcast comunicando su estado, aquellos agentes que se encuentren en su mismo domicilio cumplirán o no la cuarentena dependiendo de su responsabilidad:

- Responsabilidad alta: abortarán cualquier actividad que estén realizando y se dirigirán automáticamente a su domicilio. Además, realizará cuarentena hasta que se elimine la creencia.
- Responsabilidad media: acabarán con sus rutinas previstas para el día en curso, sin embargo, se pondrán en cuarentena a partir del día siguiente.
- Responsabilidad baja: realizarán vida normal.

Una vez un agente ha entrado en cuarentena (y por tanto tiene esa creencia), seguirá en ese estado hasta que todos los agentes infectados de su domicilio dejen de estarlo, es decir, hasta el día siguiente a que el último infectado perteneciente a su home salga del hospital.


