import org.springframework.cloud.contract.spec.Contract


/**
 * @author Aliaksandr Samal
 */
Contract.make {
    request {
        method 'POST'
        url '/api/v1/sponsors'
        body([
                name : "Aaa Bbb",
                email: "a@b.org"
        ])
        headers {
            contentType('application/json')
        }
    }
    response {
        status OK()
        body([
                id: value(consumer(42), producer(anyPositiveInt())),
        ])
        headers {
            contentType('application/json')
        }
    }
}
