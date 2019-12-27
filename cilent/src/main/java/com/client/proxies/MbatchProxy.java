package com.client.proxies;

        import com.client.bean.BatchBean;
        import com.client.bean.LibrairieBean;
        import org.springframework.cloud.netflix.ribbon.RibbonClient;
        import org.springframework.cloud.openfeign.FeignClient;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;

        import java.util.List;

@FeignClient(name="zuul-server")
@RibbonClient("microservice-mail")
public interface MbatchProxy {
        @GetMapping(value = "/microservice-mail/mail")
        void sendEmail();

        @GetMapping(value = "microservice-mail/mailAll")
        List<BatchBean> mailAll();


        @PostMapping(value = "microservice-mail/saveListBatch")
        BatchBean saveListBatch(@RequestBody BatchBean batchBean);

}
