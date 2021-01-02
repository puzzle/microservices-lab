package ch.puzzle.mm.rest.util;

import com.arjuna.ats.internal.arjuna.coordinator.CheckedActionFactoryImple;
import io.narayana.lra.client.internal.proxy.nonjaxrs.LRAParticipantRegistry;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanDefiningAnnotationBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import org.jboss.jandex.DotName;

import javax.inject.Inject;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;

public class NarayanaLRAProcessor {
    @Inject
    BuildProducer<AdditionalBeanBuildItem> additionalBeans;

    @BuildStep
    @Record(RUNTIME_INIT)
    public void build(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
        reflectiveClass.produce(new ReflectiveClassBuildItem(false, false, CheckedActionFactoryImple.class.getName()));
        additionalBeans.produce(new AdditionalBeanBuildItem(NarayanaLRAProducers.class));
    }

    @BuildStep
    BeanDefiningAnnotationBuildItem additionalBeanDefiningAnnotation() {
        return new BeanDefiningAnnotationBuildItem(DotName.createSimple(LRAParticipantRegistry.class.getName()));
    }
}