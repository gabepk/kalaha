Português/[English](https://github.com/gabepk/kalaha/blob/master/README.md)

# Kalaha

[Este é um jogo simples](https://gabepk-kalaha.herokuapp.com/) que desenvolvi em Java para o processo de contratação de uma empresa.

## :video_game: Diagrama de fluxo do jogo

![Diagrama de Fluxo](https://raw.githubusercontent.com/gabepk/kalaha/master/kalaha/WebContent/resources/img/flow-diagram.png?raw=true "Diagrama de Fluxo")

## :book: Porque eu não usei a framework Spring?

Esse é um projeto simples que não precisa ser construído sub uma grande estrutura. Frameworks são grandes pedaços de código pré-construído que você adiciona
para resolver problemas. Há alguns anos (~ 1999), quando a programação em Java para a web era complexa, precisávamos de frameworks para
tornar nossos aplicativos mais limpos, mais rápidos e fáceis de manter. Hoje em dia não é mais assim.

Exemplos de frameworks:

* JSF (v 1.0 - 2004): A especificação Java para construir a UI baseada em componentes **para webapps**. [Algumas pessoas criticam o JSF](https://dzone.com/articles/why-you-should-avoid-jsf) devido ao fato de que a interface (HTML) está intrincadamente conectada ao back-end (Bean), e portanto, os atributos front-end podem alterar o ciclo de vida do lado do servidor, violando a [separação de interesses](https://en.wikipedia.org/wiki/Separation_of_concerns). Algumas pessoas defendem o framework dizendo que ele já está atualizado para ser statefull (versão 2.0+) e para escalar bem.

* Spring (v. 1.0 - 2003): Uma estrutura de aplicativo que ajuda você a conectar e combinar diferentes componentes juntos. Faz uso pesado de anotações para minimizar a quantidade de configuração em arquivos XML. Mas isso pode tornar o código [mais difícil de depurar e manter](https://www.quora.com/What-are-some-criticisms-of-the-Spring-Framework), apontado por alguns programadores que este framework é puro [magia](http://samatkinson.com/why-i-hate-spring/) (BTW, o autor não gosta de magia e nem eu). No geral, o Spring funciona bem com bibliotecas de simulação devido ao conceito de injeção de dependência, portanto, o teste é relativamente simples.

## State**FULL** Vs. State**LESS**

Essa é a diferença de arquitetura entre um aplicativo stateful e um stateless:

* O protocolo StateFUL é um protocolo de comunicação no qual as informações da sessão de cada cliente são mantidas no lado do servidor; exemplo: JSF
* StateLESS é o oposto: a sessão pode ser armazenada, mas no lado do cliente.

Este webapp é stateful, pois o estado do jogo é armazenado na sessão do usuário.

## :anguished: O que é REST?

REST (Representational State Transfer) é um ** projeto arquitetônico ** com [6 restrições](https://restfulapi.net/rest-architectural-constraints/#uniform-interface) para criar um serviço da web:
1. Arquitetura cliente-servidor: separação de interesses para aumentar a escalabilidade (o servidor não mantém os contextos dos clientes) e a portabilidade (front-end flexível).
2. Sem estado: Nenhum contexto do cliente é armazenado no servidor entre as solicitações. O estado da sessão é mantido no cliente (geralmente nos cookies)
3. Cacheabilidade: A resposta deve ser armazenável em cache para melhoria de desempenho no lado do cliente, como a eliminação de algumas interações cliente-servidor
4. Sistema em camadas: você pode implantar a API no servidor A, armazenar dados no servidor B. autenticar no servidor C, etc.
5. Interface uniforme: simplifica e desacopla a arquitetura, permitindo que cada parte evolua independentemente
6. Código sob demanda (opcional): os servidores podem estender ou personalizar temporariamente a funcionalidade de um cliente, transferindo código executável.

RESTFull é um servidor web que implementa a arquitetura REST.
Embora este jogo use Web service + JSON, ele não é REST, já que ele não é stateless.
Depois desse projeto, percebi que nunca usei a arquitetura REST. Na verdade, meu aplicativo cliente-servidor sempre parecia
[assim](https://fernandofranzini.wordpress.com/2015/10/20/managing-http-session-em-rest-com-jax-rs/)


## :wink: Que problema resolve o REST?

Principalmente escalabilidade e portabilidade.

## :disappointed_relieved: JAX-RS (Jersey) vs Spring

* Jersey é apenas uma biblioteca para mapear endpoints e o Spring é um framework completo (BTW, [eles podem ser usados juntos](https://dzone.com/articles/lets-compare-jax-rs-vs-spring-for-rest-endpoints)).
* Testes Jersey executam um contêiner separado e testes de unidade fazem chamadas para a instância em execução de seu recurso REST. Os testes Spring MVC não exigem nenhum contêiner - e estão mais bem integrados com seus controladores. Injeção de dependência pode ser usada para injetar serviços simulados / DAOs para obter melhores testes de unidade. [Fonte](https://stackoverflow.com/questions/26824423/what-is-the-difference-among-spring-rest-service-and-jersey-rest-service-and-spr)
* Documentação sobre os projetos da Spring são mais maduros quando comparados com Jersey

Na minha conclusão, se você planeja integrar quaisquer outras bibliotecas Spring em seu aplicativo, então vá com o Spring.
Se é apenas uma simples API RESTFul, vá com jersey.

## :bomb: Qual a diferença entre REST e Queue?

REST é "async" (na verdade, é apenas um ambiente non-blocking-io-multithread; o comportamento assíncrono depende do cliente) e a fila é sync (first in, first out).

## :sweat_smile: Por que a classe Utils?

Para separar o algoritmo de jogo e o algoritmo do web service. Essa arquitetura é melhor para aplicar testes unitários.

## :art: Javascript framework vs JSF?

O JSF é bom para equipes com falta de designers front-end porque ele tem muitos componentes de interface pré-fabricados,
mas não dimensiona tão bem quanto as APIs RESTFul, que usam apenas JS + alguns frameworks.

No meu caso, eu sei o suficiente sobre front-end e back-end para construir um aplicativo com JS + Vue.js e Java.

## :chart_with_upwards_trend: O que eu poderia melhorar?

* Adicionar instruções do jogo na interface
* Construir arquitetura restfull (remove / getGameInSession, etc)
* Fazer testes de interface
* ~~Publicar online~~
