# Task

Нужно реализовать параллельный bfs. От Вас требуется написать последовательную версию алгоритма  (seq) и параллельную
версию (par). Протестировать надо на кубическом графе со стороной 500 и источником в (0, 0, 0). (Усреднить по 5
запускам) Сравнить время работы par на 4 процессах и seq на одном процессе - у Вас должно быть раза в 3 быстрее.  (Если
будет медленнее, то выставление баллов оставляется на моё усмотрение.) Учтите, что Ваш bfs должен работать на любом
графе, если Вам дан его список смежности.


# Block: Par
> Full reposrt: [12508, 11659, 11619, 11919, 11736]

> Avg: 11888.2

# Block: Seq
> Full reposrt: [31757, 36718, 25933, 30584, 26827]

> Avg: 30363.8



(Don't forget to use: -Xmx8192m -Xms8192m -XX:ActiveProcessorCount=4)