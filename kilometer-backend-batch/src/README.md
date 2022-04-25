# Batch 실행법
현재 배치는 job name을 정해주지 않으면 아무것도 실행되지 않습니다.
VMOption으로 job.name에 대해서 적어주면, 해당 job만 실행이 됩니다.

ex)-Djob.name=backend.batch.other.hello.job >>> Other Hello Batch Config에 있는 잡만 실행됩니다.